package com.example.organivy.data

data class PhotoState(

    val isLoading: Boolean = true,

    val totalPhotos: Int = 0,
    val largePhotos: Int = 0,
    val oldPhotos: Int = 0,
    val duplicatePhotos: Int = 0

)
