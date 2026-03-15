package com.example.organivy.model

data class UserProgress(
    val userId: String = "",
    val fertilizer: Int = 0,
    val waterLevel: Double = 1.0,
    val sunLightLevel: Double = 1.0,
    val plantLevel: Int = 1,
    val totalDeletedPhotos: Int = 0,
    val co2SavedGrams: Double = 0.0,
    val activeDecorations: List<String> = emptyList()

)