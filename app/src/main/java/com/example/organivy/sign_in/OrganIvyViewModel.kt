
package com.example.organivy.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organivy.SignInState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/**
 * Central ViewModel for Firebase Authentication (email/password).
 *
 * WHY THIS FILE EXISTS:
 * - Firebase Auth must not live inside Composable UI code (LoginScreen, etc.).
 *   ViewModels survive configuration changes and keep network/auth logic testable.
 * - Every screen that needs sign-in, sign-up, or password reset talks to ONE auth
 *   source so we do not create multiple FirebaseAuth listeners by accident.
 *
 * HOW IT CONNECTS TO THE REST OF THE APP:
 * - LoginScreen / SignUpScreen call signIn() / signUp() and observe [state].
 * - On success, [SignInState.isSignInSuccessful] becomes true and the UI navigates
 *   to the main app graph ("app" in MainActivity).
 * - GameViewModel separately listens for auth and syncs Firestore once a uid exists.
 */


class OrganIvyViewModel : ViewModel() {

    /** UI-facing auth result. Screens collect this Flow instead of holding auth flags locally. */

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    /**
     * Single FirebaseAuth instance for the app process.
     * getInstance() is the SDK pattern — Firebase keeps the signed-in session in memory
     * and on disk so users stay logged in between app launches.
     */

    private val auth = FirebaseAuth.getInstance()


    /**
     * Called after signIn/signUp completes.
     * WHY: Composables react to [state] rather than parsing Task callbacks in the UI layer.
     */

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }
    /** Clears one-shot success flag so navigating back to login does not immediately re-trigger navigation. */

    fun resetState() {
        _state.update { SignInState() }
    }


    /**
     * Registers a new user in Firebase Authentication.
     *
     * WHY viewModelScope + await():
     * - Firebase Tasks are callback-based; kotlinx-coroutines-play-services lets us
     *   use .await() so success/error paths stay in one try/catch block.
     * - viewModelScope cancels work if the ViewModel is destroyed (e.g. user leaves screen).
     *
     * onResult(null) means success; a non-null String is the Firebase error message for Toast.
     */


    fun signUp(email: String, password: String, username: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                // 1. Check if username is already taken in Firestore
                val existing = FirebaseFirestore.getInstance()
                    .collection("Users")
                    .whereEqualTo("name", username)
                    .get()
                    .await()

                if (!existing.isEmpty) {
                    onResult("Username is already taken")
                    return@launch
                }

                // 2. Create Firebase Auth user
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user

                // 3. Set display name (username) in Auth profile
                user?.updateProfile(userProfileChangeRequest {
                    displayName = username
                })?.await()

                // 4. Create initial User document in Firestore so we can look up email by username later
                user?.uid?.let { uid ->
                    val initialData = mapOf(
                        "name" to username,
                        "email" to email,
                        "gardenName" to "$username's Garden",
                        "Coins" to 0,
                        "co2savedGrams" to 0
                    )
                    FirebaseFirestore.getInstance().collection("Users").document(uid)
                        .set(initialData, SetOptions.merge()).await()
                }

                val userData = result.user?.let {
                    UserData(it.uid, it.displayName, it.photoUrl?.toString())
                }
                onSignInResult(SignInResult(userData, null))
                onResult(null)
            } catch (e: Exception) {
                onSignInResult(SignInResult(null, e.message))
                onResult(e.message)
            }
        }
    }

    /** Same pattern as signUp — Firebase validates credentials server-side. */

    fun signIn(usernameOrEmail: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                // 1. Determine if input is email or username
                val emailToUse = if (usernameOrEmail.contains("@")) {
                    usernameOrEmail
                } else {
                    // It's a username, look up the email in Firestore
                    val snapshot = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .whereEqualTo("name", usernameOrEmail)
                        .get()
                        .await()

                    if (snapshot.isEmpty) {
                        throw Exception("Username not found")
                    }
                    snapshot.documents.first().getString("email")
                        ?: throw Exception("User email not found")
                }

                // 2. Sign in with the resolved email
                val result = auth.signInWithEmailAndPassword(emailToUse, password).await()
                val userData = result.user?.let {
                    UserData(it.uid, it.displayName, it.photoUrl?.toString())
                }
                onSignInResult(SignInResult(userData, null))
                onResult(null)
            } catch (e: Exception) {
                onSignInResult(SignInResult(null, e.message))
                onResult(e.message)
            }
        }
    }


    /**
     * Re-checks the user's password before sensitive actions (e.g. delete account later).
     *
     * WHY reauthenticate (not just check password locally):
     * Firebase requires a recent login for destructive operations. This proves the person
     * at the device still knows the password without storing it in the app.
     */


    fun verifyPassword(
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null || user.email == null) {
            onError("No authenticated user")
            return
        }

        val credential = EmailAuthProvider.getCredential(
            user.email!!,
            password
        )
        user.reauthenticate(credential)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                onError(it.message ?: "Authentication failed")
            }
    }

    /**
     * Sends Firebase's built-in password-reset email.
     *
     * WHY Firebase handles email delivery:
     * We never build or host reset links ourselves — Firebase generates a secure link
     * and emails it, which avoids storing reset tokens in our Firestore database.
     */


    fun sendPasswordReset(email: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
                onResult(null)
            } catch (e: Exception) {
                onResult(e.message)
            }
        }
    }

    /** Ends the Firebase session locally and clears UI auth state (e.g. for a future Sign Out button). */
    fun signOut() {
        auth.signOut()
        resetState()
    }

    /** Convenience check for navigation or settings without reading Firebase directly in UI. */
    fun isSignedIn(): Boolean = auth.currentUser != null
}







