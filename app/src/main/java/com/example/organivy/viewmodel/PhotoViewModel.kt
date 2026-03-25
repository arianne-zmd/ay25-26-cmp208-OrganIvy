package com.example.organivy.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organivy.data.PhotoScanner
import com.example.organivy.data.PhotoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            //val blurDetector = BlurDetection(this@MainActivity)


            // Filtering the photos
            val largePics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.size >= 500_000 }
            }
            val oldPics = withContext(Dispatchers.Default) {
                photo.filter { pic -> pic.dateAdded <= (System.currentTimeMillis() / 1000) - 31_556_952L }
            }
            val duplicatedPics = withContext(Dispatchers.Default) {
                photo.groupBy { pic -> pic.size }.filter { pic -> pic.value.size > 1 }
            }
//                        val blurryPics = withContext(Dispatchers.Default) {
//                            photo.filter { blurDetector.isImageBlurry(it) }
//                        }


            withContext(Dispatchers.Main) {
                uiState = PhotoState(
                    isLoading = false,
                    totalPhotos = photo.size,
                    largePhotos = largePics.size,
                    oldPhotos = oldPics.size,
                    duplicatePhotos = duplicatedPics.size
                )
            }
        }
    }
}