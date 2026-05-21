
package com.example.organivy.sign_in

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organivy.SignInState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        val trimmedEmail = email.trim()
        val trimmedUsername = username.trim()

        if (trimmedEmail.isEmpty() || password.isEmpty() || trimmedUsername.isEmpty()) {
            onResult("Please fill in all fields")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            onResult("Invalid email format")
            return
        }

        if (password.length < 7) {
            onResult("Password must be at least 7 characters long")
            return
        }

        viewModelScope.launch {
            try {
                android.util.Log.d("AUTH_DEBUG", "1. Creating Auth user...")
                // 1. Create account first to get a valid token for Firestore rules
                val authResult = auth.createUserWithEmailAndPassword(trimmedEmail, password).await()
                val user = authResult.user ?: throw Exception("Failed to create user")

                try {
                    android.util.Log.d("AUTH_DEBUG", "2. Checking username availability...")
                    // 2. Check if username is taken (now authenticated)
                    val existing = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .whereEqualTo("name", trimmedUsername)
                        .get()
                        .await()

                    if (!existing.isEmpty) {
                        android.util.Log.d("AUTH_DEBUG", "Username taken, deleting auth user")
                        user.delete().await() // Clean up
                        onResult("Username is already taken")
                        return@launch
                    }

                    android.util.Log.d("AUTH_DEBUG", "3. Updating profile...")
                    user.updateProfile(userProfileChangeRequest {
                        displayName = trimmedUsername
                    }).await()

                    android.util.Log.d("AUTH_DEBUG", "4. Creating Firestore document...")
                    val initialData = mapOf(
                        "name" to trimmedUsername,
                        "email" to trimmedEmail,
                        "gardenName" to "$trimmedUsername's Garden",
                        "Coins" to 0,
                        "co2savedGrams" to 0,
                        "plantLevel" to 0,
                        "completedChallenges" to 0,
                        "CharacterColour" to "",
                        "CharacterSprite" to ""
                    )
                    FirebaseFirestore.getInstance().collection("Users").document(user.uid)
                        .set(initialData, SetOptions.merge()).await()

                    android.util.Log.d("AUTH_DEBUG", "Sign up complete!")
                    val userData = UserData(user.uid, user.displayName, user.photoUrl?.toString())
                    onSignInResult(SignInResult(userData, null))
                    onResult(null)

                } catch (inner: Exception) {
                    android.util.Log.e("AUTH_DEBUG", "Setup failed, cleaning up auth account", inner)
                    user.delete().await() // Ensure we don't leave zombie accounts
                    throw inner
                }

            } catch (e: Exception) {
                android.util.Log.e("AUTH_DEBUG", "Sign up failed", e)
                val errorMessage = if (e.message?.contains("already in use", ignoreCase = true) == true) {
                    "This email is already registered. Please log in or use another email."
                } else {
                    e.message ?: "An unknown error occurred"
                }
                onSignInResult(SignInResult(null, errorMessage))
                onResult(errorMessage)
            }
        }
    }


    /** Same pattern as signUp — Firebase validates credentials server-side. */
    fun signIn(usernameOrEmail: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                // 1. Determine if input is email or username
                val emailToUse = if (usernameOrEmail.contains("@")) {
                    usernameOrEmail.trim()
                } else {
                    // It's a username, look up the email in Firestore
                    val snapshot = FirebaseFirestore.getInstance()
                        .collection("Users")
                        .whereEqualTo("name", usernameOrEmail.trim())
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
        val trimmed = email.trim()
        if (trimmed.isEmpty()) {
            onResult("Please enter your email")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(trimmed).matches()) {
            onResult("Please enter a valid email address")
            return
        }

        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(trimmed).await()
                withContext(Dispatchers.Main) { onResult(null) }
            } catch (e: FirebaseAuthInvalidUserException) {
                withContext(Dispatchers.Main) {
                    onResult(
                        "No account found for this email. Sign up first or check the address."
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(e.message ?: "Could not send reset email. Try again.")
                }
            }
        }
    }

    /**
     * Ends the Firebase session and clears one-shot sign-in UI state.
     * onResult(null) on success; non-null String is an error message for Toast.
     */
    fun signOut(onResult: (String?) -> Unit = {}) {
        viewModelScope.launch {
            try {
                auth.signOut()
                resetState()
                onResult(null)
            } catch (e: Exception) {
                onResult(e.message ?: "Sign out failed")
            }
        }
    }

    /** Convenience check for navigation or settings without reading Firebase directly in UI. */
    fun isSignedIn(): Boolean = auth.currentUser != null
}
