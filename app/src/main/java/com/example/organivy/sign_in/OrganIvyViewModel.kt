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


class OrganIvyViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun signUp(email: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
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

    fun signIn(email: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
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
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it.message ?: "Authentication failed")
            }
    }



}


