package com.example.organivy.data

enum class ChallengeType {
    DELETE_PHOTOS,
    CLEAN_BLURRY,
    CLEAN_LARGE,
    CLEAN_OLD
}

data class Challenge(
    val id: String,
    val description: String,
    val targetValue: Int,
    val currentValue: Int = 0,
    val type: ChallengeType,
    val reward: Int,
    val isCompleted: Boolean = false
)
