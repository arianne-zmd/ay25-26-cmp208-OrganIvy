package com.example.organivy.data

data class GameState(

    val deleted: Boolean = false,

    val coins: Int = 0,
    //Sunlight = weekly helps plant
    val sunlight: Int = 0,
    //Water = daily / slow drain
    val water: Int = 0,
    val co2saved: Int = 0,
    //Fertilizer = weekly maybe. rare reward
    val fertilizer: Int = 0,
    val plantLevel: Int = 0,
    val streak: Int = 0,

    val topCategory: Int = 0,
    val picsPerWeek: Int = 0,
)
