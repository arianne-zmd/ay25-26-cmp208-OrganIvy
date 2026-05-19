package com.example.organivy

/**
 * Minimal UI state for the login/signup flow.
 *
 * WHY only two fields:
 * - isSignInSuccessful: one-shot signal for LaunchedEffect navigation after Firebase Auth.
 * - signInError: optional message if we later show inline errors (currently mostly Toast).
 */
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
