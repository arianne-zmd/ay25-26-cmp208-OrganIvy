package com.example.organivy.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions

class GardenViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    /**
     * Updates gamification stats when photos are deleted.
     * Incorporates Water (Consistency) and Sunlight (Intensity/Space Saved).
     */
    fun onPhotosDeleted(count: Int, sizeMb: Double) {
        val user = auth.currentUser ?: return
        val userRef = db.collection("Users").document(user.uid)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            
            // Get current values
            val currentPoints = snapshot.getLong("Coins") ?: 0
            val currentDeleted = snapshot.getLong("totalDeletedPhotos") ?: 0
            val currentLevel = snapshot.getLong("plantLevel") ?: 1
            val currentWater = snapshot.getDouble("waterLevel") ?: 1.0
            val currentSunlight = snapshot.getDouble("sunLightLevel") ?: 1.0

            // 1. Calculate Rewards (10 points per photo)
            val newPoints = currentPoints + (count * 10)
            val newDeleted = currentDeleted + count
            val co2Saved = newDeleted * 0.2 // Environmental Impact

            // 2. Maintenance Logic
            // Water: +5% per photo deleted
            val newWater = (currentWater + (count * 0.05)).coerceAtMost(1.0)
            // Sunlight: +1% per 1MB saved
            val newSunlight = (currentSunlight + (sizeMb * 0.01)).coerceAtMost(1.0)

            // 3. Update Firestore with new stats
            val updates = mapOf(
                "Coins" to newPoints,
                "totalDeletedPhotos" to newDeleted,
                "co2savedGrams" to co2Saved,
                "waterLevel" to newWater,
                "sunLightLevel" to newSunlight
            )
            
            transaction.set(userRef, updates, SetOptions.merge())

            // 4. Level Up Logic: Every 1000 points, the plant grows!
            if (newPoints >= currentLevel * 1000) {
                transaction.update(userRef, "plantLevel", currentLevel + 1)
            }
        }.addOnSuccessListener { 
            // Handle success (e.g., trigger a UI animation)
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }

    /**
     * Handles purchasing items from the shop using earned points.
     */
    fun buyDecoration(itemId: String, price: Int) {
        val user = auth.currentUser ?: return
        val userRef = db.collection("Users").document(user.uid)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentPoints = snapshot.getLong("Coins") ?: 0
            val ownedItems = snapshot.get("ownedDecorations") as? List<String> ?: emptyList()

            // Ensure user has enough points and doesn't already own the item
            if (currentPoints >= price && !ownedItems.contains(itemId)) {
                val newPoints = currentPoints - price
                val newOwnedItems = ownedItems + itemId
                
                transaction.update(userRef, "Coins", newPoints)
                transaction.update(userRef, "ownedDecorations", newOwnedItems)
            }
        }
    }

    /**
     * Equips or unequips a decoration for the plant.
     */
    fun toggleDecoration(itemId: String) {
        val user = auth.currentUser ?: return
        val userRef = db.collection("Users").document(user.uid)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val active = snapshot.get("activeDecorations") as? List<String> ?: emptyList()

            // If equipped, unequip it. Otherwise, equip it.
            val newActive = if (active.contains(itemId)) {
                active - itemId
            } else {
                active + itemId
            }
            
            transaction.update(userRef, "activeDecorations", newActive)
        }
    }
}
