package com.example.organivy.viewmodel

import android.app.Application
import android.content.Context
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.List

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    // I NEED TO RIGHT COMMENTS FOR THIS
    var uiState by mutableStateOf(PhotoState())
        private set

//    init {
//        loadPhotos()
//    }

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
            val duplicatedPics:  Map<Long, List<Photo>> = withContext(Dispatchers.Default) {
                photo.groupBy { pic -> pic.size }.filter { pic -> pic.value.size > 1 }
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
                        blurDetector.isImageBlurry(it)
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
                uiState = PhotoState(
                    isLoading = false,
                    totalPhotos = photo.size,
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
                    deletionList = emptyList()



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
        uiState = uiState.copy(deletionList = emptyList())
    }


}