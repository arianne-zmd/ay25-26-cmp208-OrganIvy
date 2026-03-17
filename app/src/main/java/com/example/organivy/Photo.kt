package com.example.organivy

data class Photo(
    val name: String,
    val path: String,
    val size: Long,
    val dateTaken: Long,
    val dateAdded: Long,
    val width: Int,
    val height: Int,
    val id: Long
)
