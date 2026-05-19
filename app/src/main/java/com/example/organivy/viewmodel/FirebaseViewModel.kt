package com.example.organivy.viewmodel

import android.app.Application
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.organivy.data.DeviceInfo
import com.example.organivy.data.Photo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    private val uid: String?
        get() = auth.currentUser?.uid
    val database = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    private val deviceId: String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

    val deviceInfo = DeviceInfo(
        deviceID = deviceId,
        manufacturer = Build.MANUFACTURER,
        model = Build.MODEL,
        device = Build.DEVICE,
        hardware = Build.HARDWARE,
        version = Build.VERSION.RELEASE,
        sdkInt = Build.VERSION.SDK_INT
    )


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


    fun loadSecurePhotos(
        onResult: (List<Photo>) -> Unit
    ) {

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



