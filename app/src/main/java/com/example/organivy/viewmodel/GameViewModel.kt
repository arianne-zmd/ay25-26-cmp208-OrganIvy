package com.example.organivy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.organivy.data.GameState

class GameViewModel: ViewModel () {

    var uiState by mutableStateOf(GameState())

    //coins
    fun onDeletion (){

        //not finished yet
        uiState = uiState.copy(
            coins = uiState.coins + 20
        )


    }




}