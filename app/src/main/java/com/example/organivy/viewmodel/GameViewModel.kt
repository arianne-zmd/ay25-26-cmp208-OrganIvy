package com.example.organivy.viewmodel

import android.app.Application
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
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import com.example.organivy.data.allEcoFacts

/**
 * Game progression state + **live sync** with Firestore collection "Users".
 *
 * WHY REAL-TIME LISTENERS (addSnapshotListener):
 * - Coins, CO₂, badges can change from this device or after reinstall; listeners push
 *   updates to uiState so Home/Garden screens stay in sync without manual refresh.
 *
 * WHY auth listener in init:
 * - ViewModel is created in MainActivity before the user finishes login. At that moment
 *   currentUser is often null, so observeUserData() would no-op. When login succeeds,
 *   FirebaseAuth notifies us and we attach listeners once uid exists.
 *
 * WHY isObservingFirebase flag:
 * - Prevents registering duplicate listeners if auth state fires multiple times.
 */
class GameViewModel(application: Application) : AndroidViewModel(application){

    val context = getApplication<Application>()
    var uiState by mutableStateOf(GameState())
        private set

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var isObservingFirebase = false

    init {
        generateWeeklyChallenges()

        // Start Firestore sync when user signs in; tear down flag on sign out
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                observeUserData(context)
            } else {
                isObservingFirebase = false
            }
        }
        // Cold start: user already logged in from previous session
        if (auth.currentUser != null) {
            observeUserData(context)
        }
    }

    /** Attaches three snapshot listeners: profile doc, Photos subcollection, this device doc. */
    private fun observeUserData(context: android.content.Context) {
        if (isObservingFirebase) return
        val userId = auth.currentUser?.uid ?: return
        isObservingFirebase = true
        val deviceId = Settings.Secure.getString(
            getApplication<Application>().contentResolver,
            Settings.Secure.ANDROID_ID
        )

        //Listen for the main "Users" document (Coins, co2, etc.)
        db.collection("Users").document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("Firebase", "Listen failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {

                    // load eco fact ids
                    val unlockedIds = snapshot.get("FactsGained") as? List<String> ?: emptyList()
                    // ids to EcoFacts
                    val unlockedFacts = allEcoFacts.filter { it.id in unlockedIds }

                    // Field names match Firebase exactly
                    uiState = uiState.copy(
                        userName = snapshot.getString("name") ?: "",
                        // added this 
                        gardenName = snapshot.getString("gardenName") ?: "My Garden",
                        coins = (snapshot.getLong("Coins")?.toInt() ?: 0),
                        co2saved = (snapshot.getLong("co2savedGrams")?.toInt() ?: 0),
                        streak = (snapshot.getLong("totalDeletedPhotos")?.toInt() ?: 0),
                        badgesEarned = snapshot.getString("BadgesEarned") ?: "",
                        //factsGained = snapshot.getString("FactsGained") ?: "",
                        characterColour = snapshot.getString("CharacterColour") ?: "",
                        characterSprite = snapshot.getString("CharacterSprite") ?: R.drawable.character_base_single_green.toString(),
                        unlockedEcoFacts = unlockedFacts,
                        completedChallenges = (snapshot.getLong("completedChallenges")?.toInt() ?: 0),
                        plantLevel = (snapshot.getLong("plantLevel")?.toInt() ?: 0),
                        grownPlants = (snapshot.getLong("grownPlants")?.toInt() ?: 0)

                    )

                }


            }

        // Separate listener for the "Photos" sub-collection
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

        db.collection("Users").document(userId).collection("devices")
            .document(deviceId).addSnapshotListener { snapshot, e ->

                if (e != null) {
                    Log.w("Firebase", "Device listen failed", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {

                    val localDeletedPhotos =
                        snapshot.getLong("localDeletedPhotos")?.toInt() ?: 0
                    val localDeletedPhotoBytes =
                        snapshot.getLong("localDeletedPhotoBytes")?.toLong() ?: 0
                    val totalCO2Saved: Double =
                        (snapshot.getDouble("totalCO2Saved")?.toDouble() ?: 0) as Double




                    uiState = uiState.copy(
                        localDeletedPhotos = localDeletedPhotos,
                        localDeletedPhotoBytes = localDeletedPhotoBytes,
                        localco2saved = totalCO2Saved
                    )

                }
            }


    }



    //CHALLENGESSSSSSSSS
    private fun generateWeeklyChallenges() {
        val pool = listOf(
            //Delete Photos Challenge
            Challenge("1", "Delete 5 photos", 5, 0, ChallengeType.DELETE_PHOTOS, 50),
            Challenge("2", "Clear 10 photos", 10, 0, ChallengeType.DELETE_PHOTOS, 100),
            Challenge("3", "Delete 15 photos", 15, 0, ChallengeType.DELETE_PHOTOS, 125),
            Challenge("4", "Delete 20 photos", 20, 0, ChallengeType.DELETE_PHOTOS, 150),

            //Blurry Images Challenges
            Challenge("5", "Clean 5 blurry images", 5, 0, ChallengeType.CLEAN_BLURRY, 50),
            Challenge("6", "Clean 10 blurry images", 10, 0, ChallengeType.CLEAN_BLURRY, 100),
            Challenge("7", "Clean 15 blurry images", 15, 0, ChallengeType.CLEAN_BLURRY, 125),
            Challenge("8", "Clean 20 blurry images", 20, 0, ChallengeType.CLEAN_BLURRY, 150),

            //Large Images Challenges
            Challenge("9", "Clean 5 large images", 5, 0, ChallengeType.CLEAN_LARGE, 75),
            Challenge("10", "Clean 10 large images", 10, 0, ChallengeType.CLEAN_LARGE, 125),
            Challenge("11", "Clean 15 large images", 15, 0, ChallengeType.CLEAN_LARGE, 175),
            Challenge("12", "Clean 20 large images", 20, 0, ChallengeType.CLEAN_LARGE, 225),

            //Old Images Challenges
            Challenge("13", "Delete 5 old images", 5, 0, ChallengeType.CLEAN_OLD, 50),
            Challenge("14", "Clear 10 old images", 10, 0, ChallengeType.CLEAN_OLD, 100),
            Challenge("15", "Delete 20 old images", 20, 0, ChallengeType.CLEAN_OLD, 150),


            )

        uiState = uiState.copy(challenges = pool.shuffled().take(3))
    }




    // Update challenge progress
    fun updateChallengeProgress(
        type: ChallengeType,
        amount: Int
    ) {

        var rewardCoins = 0
        var completedCountIncrease = 0

        val updatedChallenges = uiState.challenges.map { challenge ->

            if (
                challenge.type == type &&
                !challenge.isCompleted
            ) {

                val newProgress =
                    (challenge.currentValue + amount)
                        .coerceAtMost(challenge.targetValue)

                val completedNow =
                    newProgress >= challenge.targetValue

                // reward only once
                if (completedNow && !challenge.isCompleted) {
                    rewardCoins += challenge.reward
                    completedCountIncrease++
                }

                challenge.copy(
                    currentValue = newProgress,
                    isCompleted = completedNow
                )

            } else {
                challenge
            }
        }

        // total completed challenges
        val newCompletedChallenges =
            uiState.completedChallenges + completedCountIncrease

        // calculate plant level
        val newPlantLevel =
            calculatePlantLevel(newCompletedChallenges)

        uiState = uiState.copy(
            challenges = updatedChallenges,

            // reward coins
            coins = uiState.coins + rewardCoins,

            // plant progression
            completedChallenges = newCompletedChallenges,
            plantLevel = newPlantLevel
        )
        saveUserDataToFirebase()
    }

    // PLANT LEVEL THRESHOLDS TO LEVEL UP
    val plantThresholds = listOf(
        3,
        8,
        15,
        24,
        35
    )

    private fun calculatePlantLevel(completedChallenges: Int): Int {

        var level = 0

        for (threshold in plantThresholds) {
            if (completedChallenges >= threshold) {
                level++
            }
        }

        return level
    }




    //ECO FACTSSSSS
    fun unlockRandomEcoFact() {

        // facts not unlocked yet
        val lockedFacts = allEcoFacts.filter {
            it !in uiState.unlockedEcoFacts
        }

        if (lockedFacts.isEmpty()) return

        // 30% chance
        val shouldUnlock = (1..100).random() <= 30

        if (shouldUnlock) {

            val randomFact = lockedFacts.random()

            uiState = uiState.copy(

                unlockedEcoFacts =
                    uiState.unlockedEcoFacts + randomFact,

                newlyUnlockedFact = randomFact
            )

            //saveEcoFactsToFirebase()
        }

    }
    // hide pop up
    fun clearUnlockedFactPopup() {

        uiState = uiState.copy(
            newlyUnlockedFact = null
        )
    }


    // ON DELETEEEEEEEEEEEEEEEE
    //coins
    fun onDeletion (photoNum: Int){
        // 1. Update local coins (The listener will sync back later, but we push immediately)
        val newCoins = uiState.coins + 20 + (photoNum * 5)

        uiState = uiState.copy(
            coins = newCoins
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


    fun updateCharacterColour(colour: String, sprite: String) {
        uiState = uiState.copy(
            characterColour = colour,
            characterSprite = sprite
        ) //ADDED THIS
        saveUserDataToFirebase()
    }

    fun updateCharacterSprite(spriteResId: Int) {// SOMETHING OPR ANOYTHER
        uiState = uiState.copy(
            characterSprite = spriteResId.toString()
        )
        saveUserDataToFirebase()
    }

//added this 
    fun updateGardenName(newName: String) {
        uiState = uiState.copy(gardenName = newName)
        saveUserDataToFirebase()
    }

    // --- Firebase write paths (push local state → cloud) ---

    /**
     * Persists onboarding / profile choices to Users/{uid}.
     *
     * WHY SetOptions.merge():
     * Only updates fields we send; does not wipe garden stats (points, waterLevel) that
     * GardenViewModel may have written to the same document.
     */
    fun saveUserDataToFirebase() {
        val userId = auth.currentUser?.uid ?: return

        val userData = mapOf(
            "name" to uiState.userName,
            //and this
            "gardenName" to uiState.gardenName,
            "Coins" to uiState.coins,
            "co2savedGrams" to uiState.co2saved,
            "totalDeletedPhotos" to uiState.streak,
            "BadgesEarned" to uiState.badgesEarned,
            "FactsGained" to uiState.factsGained,
            "CharacterColour" to uiState.characterColour,
            "CharacterSprite" to uiState.characterSprite,
            "completedChallenges" to uiState.completedChallenges,
            "plantLevel" to uiState.plantLevel,
            "grownPlants" to uiState.grownPlants
        )

        db.collection("Users").document(userId)
            .set(userData, SetOptions.merge())
            .addOnSuccessListener { Log.d("Firebase", "User data saved!") }
            .addOnFailureListener { e -> Log.w("Firebase", "Error saving user data", e) }
    }

    /** Updates only FactsGained array when user unlocks eco facts in the journal. */
    fun saveEcoFactsToFirebase() {

        val userId =
            auth.currentUser?.uid ?: return

        val factIds =
            uiState.unlockedEcoFacts.map { it.id }

        db.collection("Users")
            .document(userId)
            .update(
                "FactsGained",
                factIds
            )
    }
}
