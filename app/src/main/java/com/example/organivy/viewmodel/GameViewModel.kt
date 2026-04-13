package com.example.organivy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.organivy.data.Challenge
import com.example.organivy.data.ChallengeType
import com.example.organivy.data.GameState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GameViewModel: ViewModel () {

    var uiState by mutableStateOf(GameState())
        private set

    init {
        generateWeeklyChallenges()
        loadUserDataFromFirebase()
    }

    private fun generateWeeklyChallenges() {
        val pool = listOf(
            Challenge("1", "Delete 5 photos", 5, 0, ChallengeType.DELETE_PHOTOS, 50),
            Challenge("2", "Clear 10 photos", 10, 0, ChallengeType.DELETE_PHOTOS, 100),
            Challenge("3", "Delete 20 photos", 20, 0, ChallengeType.DELETE_PHOTOS, 150)
        )
        // Pick 3 random challenges from the pool
        uiState = uiState.copy(challenges = pool.shuffled().take(3))
    }

    //coins
    fun onDeletion (photoNum: Int){

        // 1. Update coins
        val newCoins = uiState.coins + 20 + (photoNum * 5)

        // 2. Update challenge progress
        val updatedChallenges = uiState.challenges.map { challenge ->
            if (challenge.type == ChallengeType.DELETE_PHOTOS && !challenge.isCompleted) {
                val newProgress = (challenge.currentValue + photoNum).coerceAtMost(challenge.targetValue)
                challenge.copy(
                    currentValue = newProgress,
                    isCompleted = newProgress >= challenge.targetValue
                )
            } else {
                challenge
            }
        }

        uiState = uiState.copy(
            coins = newCoins,
            challenges = updatedChallenges
        )
        saveUserDataToFirebase()
    }

    fun spendCoins(amount: Int) {
        if (uiState.coins >= amount) {
            uiState = uiState.copy(
                coins = uiState.coins - amount
            )
            saveUserDataToFirebase()
        }
    }

    // --- Customization Functions ---
    fun updateBase(resId: Int) {
        uiState = uiState.copy(userBase = resId)
    }

    fun updateHair(resId: Int) {
        uiState = uiState.copy(userHair = resId)
    }

    fun updateOutfit(resId: Int?) {
        uiState = uiState.copy(userOutfit = resId)
    }

    // --- Firebase Integration ---
    fun saveUserDataToFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        
        val userData = mapOf(
            "coins" to uiState.coins,
            "userBase" to uiState.userBase,
            "userHair" to uiState.userHair,
            "userOutfit" to uiState.userOutfit,
            "co2saved" to uiState.co2saved,
            "plantLevel" to uiState.plantLevel
        )
        
        db.collection("users").document(userId)
            .set(userData, com.google.firebase.firestore.SetOptions.merge())
            .addOnSuccessListener { Log.d("Firebase", "User data saved!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error saving user data", e) }
    }

    fun loadUserDataFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    uiState = uiState.copy(
                        coins = (document.getLong("coins") ?: 0L).toInt(),
                        userBase = (document.getLong("userBase") ?: uiState.userBase.toLong()).toInt(),
                        userHair = (document.getLong("userHair") ?: uiState.userHair.toLong()).toInt(),
                        userOutfit = document.getLong("userOutfit")?.toInt(),
                        co2saved = (document.getLong("co2saved") ?: 0L).toInt(),
                        plantLevel = (document.getLong("plantLevel") ?: 0L).toInt()
                    )
                }
            }
            .addOnFailureListener { e -> Log.w("Firebase", "Error loading user data", e) }
    }
}
