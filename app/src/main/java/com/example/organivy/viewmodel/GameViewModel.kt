package com.example.organivy.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.organivy.R
import com.example.organivy.data.Challenge
import com.example.organivy.data.ChallengeType
import com.example.organivy.data.GameState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class GameViewModel: ViewModel () {

    var uiState by mutableStateOf(GameState())
        private set

    // Connecting to the Users collection on firestore
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        generateWeeklyChallenges()
        observeUserData()
    }

    private fun observeUserData() {
        val userId = auth.currentUser?.uid ?: return

        // 1. Listen for the main "Users" document (Coins, co2, etc.)
        db.collection("Users").document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("Firebase", "Listen failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    // Note: Field names match Firebase exactly (Case-Sensitive)
                    uiState = uiState.copy(
                        userName = snapshot.getString("name") ?: "",
                        coins = (snapshot.getLong("Coins")?.toInt() ?: 0),
                        co2saved = (snapshot.getLong("co2savedGrams")?.toInt() ?: 0),
                        streak = (snapshot.getLong("totalDeletedPhotos")?.toInt() ?: 0),
                        badgesEarned = snapshot.getString("BadgesEarned") ?: "",
                        factsGained = snapshot.getString("FactsGained") ?: "",
                        characterSprite = snapshot.getString("CharacterSprite") ?: "R.drawable.character_base_single_green()",
                    )

                }
            }

        // 2. Separate listener for the "Photos" sub-collection
        db.collection("Users").document(userId).collection("Photos")
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Log.w("Firebase", "Photos sub-collection listen failed", e)
                    return@addSnapshotListener
                }
                
                val photoDoc = querySnapshot?.documents?.firstOrNull()
                if (photoDoc != null) {
                    val camera = photoDoc.getLong("Camera")?.toInt() ?: 0
                    val screenshots = photoDoc.getLong("Screenshots")?.toInt() ?: 0
                    val downloads = photoDoc.getLong("Downloads")?.toInt() ?: 0
                    val blurry = photoDoc.getLong("Blurry Images")?.toInt() ?: 0
                    
                    // Summing categories to show total pics processed
                    uiState = uiState.copy(
                        picsDelPerWeek = camera + screenshots + downloads + blurry
                    )
                }
            }
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
        // 1. Update local coins (The listener will sync back later, but we push immediately)
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
    }

    fun spendCoins(amount: Int) {
        if (uiState.coins >= amount) {
            uiState = uiState.copy(
                coins = uiState.coins - amount
            )
        }
    }

    // --- Customization Functions ---
   /* fun updateCharacterSprite(resId: Int) {
        uiState = uiState.copy(characterSprite = resId)
        saveUserDataToFirebase()
    }


    */


    // --- Firebase Integration ---
    fun saveUserDataToFirebase() {
        val userId = auth.currentUser?.uid ?: return
        
        val userData = mapOf(
            "name" to uiState.userName,
            "Coins" to uiState.coins,
            "co2savedGrams" to uiState.co2saved,
            "totalDeletedPhotos" to uiState.streak,
            "BadgesEarned" to uiState.badgesEarned,
            "FactsGained" to uiState.factsGained,
            "CharacterSprite" to uiState.characterSprite
        )
        
        db.collection("Users").document(userId)
            .set(userData, SetOptions.merge())
            .addOnSuccessListener { Log.d("Firebase", "User data saved!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error saving user data", e) }
    }
}
