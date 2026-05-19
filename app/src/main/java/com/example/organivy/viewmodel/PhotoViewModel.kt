package com.example.organivy.viewmodel

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organivy.data.BlurDetection
import com.example.organivy.data.Photo
import com.example.organivy.data.PhotoScanner
import com.example.organivy.data.PhotoState
import com.example.organivy.data.SafeDeletion
import com.example.organivy.ui.components.BottomNavItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.List

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // I NEED TO RIGHT COMMENTS FOR THIS
    var uiState by mutableStateOf(PhotoState())
        private set


    init {
        loadPhotos()

    }

    fun loadPhotos() {
        // MOVED WHAT WAS IN MAIN KT HEREEEEEEEEEEE
        viewModelScope.launch(Dispatchers.IO) {

            val scanner = PhotoScanner(context)
            val photo = scanner.logScanImages()
            //val blurDetector = BlurDetection(context)


            // Filtering the photos types
            val largePics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.size >= 500_000 }
            }
            val oldPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.dateAdded <= (System.currentTimeMillis() / 1000) - 31_556_952L }
            }
            val duplicatedPics:  Map<Pair<Long, String>, List<Photo>> = withContext(Dispatchers.Default) {
                photo.groupBy { pic -> pic.size to pic.name }.filter { pic -> pic.value.size > 1 }
            }


            // 2️ Flatten duplicates into a list
            val allDuplicates: List<Photo> = withContext(Dispatchers.Default) {
                duplicatedPics.flatMap { entry -> entry.value } // flatMap over map entries
            }


//            val blurryPics = withContext(Dispatchers.Default) {
//                photo.chunked(100).flatMap { batch ->
//                    batch.filter { blurDetector.isImageBlurry(it) }
//                }
//            }

            // Filtering the photos cateries
            val camPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.path.contains("DCIM/Camera") }
            }
            val sSPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.path.contains("/Screenshots") }
            }
            val dPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.path.contains("Download/") }
            }
            val wIPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.path.contains("/WhatsApp Images") }
            }

            //addddddddddddddd
            viewModelScope.launch(Dispatchers.Default) {

                val blurDetector = BlurDetection(context)
                val result = mutableListOf<Photo>()


                photo.chunked(50).forEachIndexed { index, batch ->

                    val blurryBatch = batch.filter {
                        //blurDetector.isImageBlurry(it)
                        try {
                            blurDetector.isImageBlurry(it)
                        } catch (e: Exception) {

                            Log.e("BlurCrash", "Failed to process image", e)

                            false
                        }
                    }

                    result.addAll(blurryBatch)

                    //  Update UI while app is running
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(
                            blurryPhotos = result.size,
                            blurryPicsList = result.toList()
                        )
                    }

                    Log.d("BlurTest", "Batch $index done")

                    // small pause so UI stays smooth
                    kotlinx.coroutines.delay(10)
                }
            }
            //addeddddddddddd




            withContext(Dispatchers.Main) {
                uiState = uiState.copy(//PhotoState(
                    isLoading = false,
                    totalPhotos = photo,
                    largePhotos = largePics.size,
                    oldPhotos = oldPics.size,
                    duplicatePhotos = duplicatedPics.size,
                    //blurryPhotos = blurryPics.size,

                    //lists
                    oldPicsList = oldPics,
                    largePicsList  = largePics,
                    duplicatePicsList = allDuplicates,
                    //blurryPicsList = blurryPics,

                    cameraPicsList = camPics,
                    screenshotsList = sSPics,
                    downloadsList = dPics,
                    whatsappPicsList = wIPics,

                    //deletion list
                    deletionList = emptyList(),
                    //totalDeletedPics = 0,
                    //totalDeletedBytes = 0

                    //secure list
                    //secureFolderList = emptyList()
                    //secureFolderList = uiState.secureFolderList



                )


            }
        }


    }

    fun onPhotoChecked(photo: Photo, isChecked: Boolean) {
        uiState = if (isChecked) {
            uiState.copy(deletionList = uiState.deletionList + photo)
        } else {
            uiState.copy(deletionList = uiState.deletionList - photo)
        }
    }

    /**
     * Toggles secure-folder membership locally and mirrors to Firestore via FirebaseViewModel.
     * WHY immediate upload: cloud backup matches user's checkbox; unchecked removes cloud doc.
     */
    fun onSecurePhotoChecked(photo: Photo, isChecked: Boolean, firebaseViewModel: FirebaseViewModel) {
        uiState = if (isChecked) {
            val updated = uiState.secureFolderList + photo

            firebaseViewModel.uploadPhoto(photo)

            uiState.copy(secureFolderList = updated)

        } else {
            val updated = uiState.secureFolderList - photo

            firebaseViewModel.deletePhoto(photo)

            uiState.copy(secureFolderList = updated)
        }
    }

    fun removeDeletedPhotosFromSecureFolder() {

        val deletedPhotos = uiState.deletionList

        uiState = uiState.copy(
            secureFolderList =
                uiState.secureFolderList - deletedPhotos.toSet()
        )
    }


    //idk
    fun deleteSelectedPhotos(
        safeDeletion: SafeDeletion,
        launcher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val photosToDelete = uiState.deletionList

        if (photosToDelete.isEmpty()) {
            android.util.Log.d("DeleteTest", "No photos selected for deletion")
            return
        }

        Log.d("DeleteTest", "Inside deleteSelectedPhotos")

        val uris = photosToDelete.map { safeDeletion.photoUri(it) }
        Log.d("DeleteTest", "Deleting ${uris.size} photos")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intentSender = MediaStore.createDeleteRequest(
                getApplication<Application>().contentResolver,
                uris
            ).intentSender
            val request = IntentSenderRequest.Builder(intentSender).build()
            launcher.launch(request)
        }
    }
    fun clearDeletionList() {


        val deletedPhotosCount = uiState.deletionList.size
        val deletedPhotosBytes = uiState.deletionList.sumOf { it.size }


        val co2Saved =
            (deletedPhotosBytes/ 1_000_000) * 0.00294




        uiState = uiState.copy(


            //total  deleted amount
            totalDeletedPics =
                uiState.totalDeletedPics + deletedPhotosCount,
            // total deleted bytes amount
            totalDeletedBytes = uiState.totalDeletedBytes + deletedPhotosBytes,


            // co2 saved
            totalCO2Saved = co2Saved,


            deletionList = emptyList())
    }


    /*fun uploadSecureFolderToFirebase(firebaseViewModel: FirebaseViewModel) {


        uiState.secureFolderList.forEach { photo ->


            firebaseViewModel.uploadPhoto(photo)
        }
    }*/


//good
    /** Pulls secure-folder list from Firestore when SecureFolderPage opens. */
    fun loadSecurePhotos(firebaseViewModel: FirebaseViewModel) {
        firebaseViewModel.loadSecurePhotos { photos ->
            uiState = uiState.copy(secureFolderList = photos)
        }
    }


    /**
     * Persists deletion totals under Users/{uid}/devices/{androidId}.
     *
     * WHY per-device subcollection (not only on Users/{uid}):
     * - GameViewModel.listen on devices/{deviceId} shows stats for THIS phone.
     * - Global totalDeletedPhotos on the parent doc can stay for cross-device badges.
     */
    fun saveDeletedCountToFirebase() {


        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val deviceId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )


        val db = FirebaseFirestore.getInstance()


        /* db.collection("Users")
             .document(userId)
             .update(
                 "totalDeletedPhotos",
                 uiState.totalDeletedPics
             )
 */


        db.collection("Users")
            .document(userId)
            .collection("devices")
            .document(deviceId)
            .update(
                "localDeletedPhotos", uiState.totalDeletedPics,
                "localDeletedPhotoBytes", uiState.totalDeletedBytes,
                "totalCO2Saved", uiState.totalCO2Saved,
            )
    }






}
