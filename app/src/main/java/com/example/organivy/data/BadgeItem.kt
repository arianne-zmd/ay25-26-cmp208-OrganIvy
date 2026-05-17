package com.example.organivy.data

data class BadgeItem(
    val imageRes: Int,
    val name: String,
    val description: String,

    val amountToEarn: Long,
    val badgeType: BadgeType
)

enum class BadgeType {

    PHOTO_DELETE,
    BYTES_DELETE
}
