package com.example.organivy.sign_in

/**
 * Result wrapper returned from OrganIvyViewModel after signIn/signUp.
 *
 * WHY separate from SignInState:
 * - SignInResult is the outcome of one operation (data + error).
 * - SignInState is what the UI collects over time (success flag for navigation).
 */
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

/** Subset of FirebaseUser fields the UI might display (profile, future avatar). */
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
