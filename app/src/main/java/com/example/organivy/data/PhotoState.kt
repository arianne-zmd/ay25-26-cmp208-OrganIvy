package com.example.organivy.data

data class PhotoState(

    val isLoading: Boolean = true,

    val totalPhotos: Int = 0,
    val largePhotos: Int = 0,
    val oldPhotos: Int = 0,
    val duplicatePhotos: Int = 0,

    // to list the different types and catergories of photos
    // types
    val oldPicsList: List<Photo> = emptyList(),
    val largePicsList: List<Photo> = emptyList(),

    // catergories
    val cameraPicsList: List<Photo> = emptyList(),
    val screenshotsList: List<Photo> = emptyList(),
    val downloadsList: List<Photo> = emptyList(),
    val whatsappPicsList: List<Photo> = emptyList(),

    //deletion list
    val deletionList: List<Photo> = emptyList()


)
