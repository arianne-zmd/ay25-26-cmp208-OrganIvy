package com.example.organivy.ui.pages

import com.example.organivy.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.VerticalDivider
import coil.compose.AsyncImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.data.ShopItem
import com.example.organivy.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShopScreen(
    gameViewModel: GameViewModel = viewModel(),
    onNavigateToProfile: () -> Unit
) {
    var showPlantPots by remember { mutableStateOf(false) }
    var showFlowers by remember { mutableStateOf(false) }
    var selectedPot by remember { mutableStateOf<Int?>(null) }
    var selectedFlower by remember { mutableStateOf<Int?>(null) }
    val coins = gameViewModel.uiState.coins


    val potImages = listOf(
        ShopItem(R.drawable.pot_1, 50, "Standard Pot")
    )

    val flowerImages = listOf(
        ShopItem(R.drawable.daisy_1, 20, "Daisy"),
        ShopItem(R.drawable.lily_1, 20, "Lily"),
        ShopItem(R.drawable.mushroom_1, 20, "Mushroom"),
        ShopItem(R.drawable.rose_1, 20, "Rose"),
        ShopItem(R.drawable.sunflower_1, 20, "Sunflower"),
        ShopItem(R.drawable.tulip_1, 20, "Tulip")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Stage area in the middle (Weighted to take remaining space)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter // Pushes content to the bottom (near buttons)
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 8.dp), // Small gap above the buttons
                contentAlignment = Alignment.BottomCenter
            ) {
                // Pot at the bottom layer
                selectedPot?.let { potRes ->
                    AsyncImage(
                        model = potRes,
                        contentDescription = "Selected Pot",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                // Flower on top layer
                selectedFlower?.let { flowerRes ->
                    // Adjust offset dynamically based on the flower type
                    // You can change these specific amounts to position the mushroom perfectly
                    val (xOffset, yOffset) = when (flowerRes) {
                        R.drawable.mushroom_1 -> (-5).dp to (-70).dp
                        else -> 0.dp to (-92).dp
                    }

                    AsyncImage(
                        model = flowerRes,
                        contentDescription = "Selected Flower",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.BottomCenter)
                            .offset(x = xOffset, y = yOffset)
                    )

                }
            }
        }

        // Control buttons (Row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { 
                    showPlantPots = !showPlantPots
                    if (showPlantPots) showFlowers = false
                },
                modifier = Modifier.height(45.dp)
            ) {
                Text(text = if (showPlantPots) "Hide Pots" else "Show Plant Pots")
            }

            Spacer(modifier = Modifier.width(16.dp))
            
            VerticalDivider(
                modifier = Modifier
                    .height(30.dp)
                    .width(2.dp),
                color = MaterialTheme.colorScheme.outline
            )
            
            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    showFlowers = !showFlowers
                    if (showFlowers) showPlantPots = false
                },
                modifier = Modifier.height(45.dp)
            ) {
                Text(text = if (showFlowers) "Hide Flowers" else "Flowers")
            }
        }

        // Grid area at the bottom (Under the buttons)
        if (showPlantPots) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(potImages) { item ->
                    ShopGridItem(
                        item = item,
                        userCoins = coins,
                        onBuy = {
                            gameViewModel.spendCoins(item.price)
                            selectedPot = item.imageRes
                        }
                    )
                }
            }
        } else if (showFlowers) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(flowerImages) { item ->
                    ShopGridItem(
                        item = item,
                        userCoins = coins,
                        onBuy = {
                            gameViewModel.spendCoins(item.price)
                            selectedFlower = item.imageRes
                        }
                    )
                }
            }
        } else {
            // Placeholder spacer to keep buttons in the same position
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}

@Composable
fun ShopGridItem(item: ShopItem, userCoins: Int, onBuy: () -> Unit){
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            AsyncImage(model = item.imageRes,modifier = Modifier.size(60.dp), contentDescription = null)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🪙 ${item.price}", fontSize = 12.sp)

            }
            Button(
                onClick =onBuy,
                enabled = userCoins >= item.price,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Buy", fontSize = 10.sp)
            }
        }

    }
}
