package com.example.organivy.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun StatsandImpactPage(onNavigateToProfile: () -> Unit,
                       photoViewModel: PhotoViewModel,
                       gameViewModel: GameViewModel,
                       ) {

    val state = photoViewModel.uiState
    val state2 = gameViewModel.uiState

    Text("User ")

    Text("User ")
    Spacer(modifier = Modifier.height(20.dp))
    Text("Total deleted images across devices ")
    Spacer(modifier = Modifier.height(20.dp))
    Text("Total estimated co2 saved across devices ")
    Spacer(modifier = Modifier.height(20.dp))
    Text("Total delete images : ${state.totalDeletedPics}")
    Spacer(modifier = Modifier.height(20.dp))
    Text("Total estimated co2 saved ")


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Stats and Impact Page")
    }
}
