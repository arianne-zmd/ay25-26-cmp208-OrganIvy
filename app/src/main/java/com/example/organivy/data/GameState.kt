package com.example.organivy.data

import com.example.organivy.R

data class GameState(
    val userName: String = "",
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

    val badgesEarned: String = "",
    val factsGained: String = "",
    val characterColour: String = "",
    val characterSprite: String = "",


    val topCategory: Int = 0,
    val picsDelPerWeek: Int = 0,
    val challenges: List<Challenge> = emptyList(),

    )