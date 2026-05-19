package com.example.organivy.viewmodel

import android.app.Application
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import com.example.organivy.data.DeviceInfo
import com.example.organivy.data.Photo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Firestore access for the **Secure Folder** feature (photo metadata backup).
 *
 * WHY A SEPARATE ViewModel FROM GameViewModel:
 * - GameViewModel owns coins, badges, challenges — game progression.
 * - Secure folder stores per-photo records under the user's device — cleaning feature.
 *   Splitting responsibilities keeps each class smaller and avoids one giant Firebase class.
 *
 * WHY AndroidViewModel (not plain ViewModel):
 * - We need [applicationContext] for ANDROID_ID and device info without leaking an Activity.
 *
 * FIRESTORE PATH (must match GameViewModel / PhotoViewModel):
 *   Users/{uid}/devices/{androidId}/securePhotos/{photoId}
 *
 * Collection name is "Users" (capital U) — Firestore paths are case-sensitive; using
 * "users" elsewhere would write to a different database tree and data would never sync.
 */
class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    /** uid is null when logged out — every write method returns early instead of throwing. */
    private val uid: String?
        get() = auth.currentUser?.uid

    val database = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    /**
     * Stable per-install device id.
     * WHY: One user can have phone + tablet; stats and secure photos are scoped per device
     * so reinstalling on a new phone does not overwrite another device's secure list.
     */
    private val deviceId: String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    /** Optional metadata about this device (for future analytics or support screens). */
    val deviceInfo = DeviceInfo(
        deviceID = deviceId,
        manufacturer = Build.MANUFACTURER,
        model = Build.MODEL,
        device = Build.DEVICE,
        hardware = Build.HARDWARE,
        version = Build.VERSION.RELEASE,
        sdkInt = Build.VERSION.SDK_INT
    )

    /**
     * Called when user checks a photo into the secure folder.
     *
     * WHY metadata only (not the image file yet):
     * Storing path/size/dates in Firestore is cheap and fast. Full image upload would use
     * Firebase Storage (dependency is already added in build.gradle for future use).
     *
     * document(photo.id.toString()) — one doc per photo so updates/deletes are idempotent.
     */
    fun uploadPhoto(photo: Photo) {
        val userId = uid ?: return

        val data = hashMapOf(
            "name" to photo.name,
            "path" to photo.path,
            "size" to photo.size,
            "dateTaken" to photo.dateTaken,
            "dateAdded" to photo.dateAdded,
            "width" to photo.width,
            "height" to photo.height,
            "id" to photo.id
        )

        database.collection("Users")
            .document(userId)
            .collection("devices")
            .document(deviceId)
            .collection("securePhotos")
            .document(photo.id.toString())
            .set(data)
    }

    /** Removes secure-folder entry when user unchecks a photo locally. */
    fun deletePhoto(photo: Photo) {
        val userId = auth.currentUser?.uid ?: return

        database.collection("Users")
            .document(userId)
            .collection("devices")
            .document(deviceId)
            .collection("securePhotos")
            .document(photo.id.toString())
            .delete()
    }

    /**
     * One-shot read of all secure photos for this device (used when opening Secure Folder screen).
     *
     * WHY .get() instead of addSnapshotListener here:
     * PhotoViewModel calls this on screen open; a single fetch is enough. GameViewModel uses
     * listeners where live updates matter (coins, stats).
     */
    fun loadSecurePhotos(onResult: (List<Photo>) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        database.collection("Users")
            .document(userId)
            .collection("devices")
            .document(deviceId)
            .collection("securePhotos")
            .get()
            .addOnSuccessListener { result ->
                val photos = result.documents.mapNotNull {
                    it.toObject(Photo::class.java)
                }
                onResult(photos)
            }
    }
}
