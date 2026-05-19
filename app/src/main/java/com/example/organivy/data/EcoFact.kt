package com.example.organivy.data

data class EcoFact(
    val id: String,
    val title: String,
    val fact: String,
    val source: String,
    val imageRes: Int = 0
)
