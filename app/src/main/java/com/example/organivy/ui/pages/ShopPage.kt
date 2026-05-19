package com.example.organivy.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.organivy.R
import com.example.organivy.data.ShopItem
import com.example.organivy.ui.components.SpriteCharacter
import com.example.organivy.viewmodel.GameViewModel

@Composable
fun ShopScreen(
    gameViewModel: GameViewModel = viewModel(),
    onNavigateToProfile: () -> Unit = {}
) {
    val uiState = gameViewModel.uiState
    val currentColor = uiState.characterColour
    val coins = uiState.coins

    var showPlantPots by remember { mutableStateOf(false) }
    var showFlowers by remember { mutableStateOf(false) }
    var showAccessories by remember { mutableStateOf(false) }

    var selectedPot by remember { mutableStateOf<Int?>(null) }
    var selectedFlower by remember { mutableStateOf<Int?>(null) }
    var selectedAccessory by remember { mutableStateOf<Int?>(null) }

    val baseCharacterResource = when (currentColor) {
        "Blue" -> R.drawable.character_base_single_blue
        "Pink" -> R.drawable.character_base_single_pink
        "Orange" -> R.drawable.character_base_single_orange
        "Purple" -> R.drawable.character_base_single_purple
        "Yellow" -> R.drawable.character_base_single_yellow
        else -> R.drawable.character_base_single_green
    }

    fun getComposedSprite(item: ShopItem, color: String): Int {
        return when (item.name) {
            "Pink Bow" -> when (color) {
                "Blue" -> R.drawable.character_bow_still_blue
                "Pink" -> R.drawable.character_bow_still_pink
                "Yellow" -> R.drawable.character_bow_still_yellow
                "Purple" -> R.drawable.character_bow_still_purple
                "Orange" -> R.drawable.character_bow_still_orange
                else -> R.drawable.character_bow_still_green
            }
            "Cowboy Hat" -> when (color) {
                "Blue" -> R.drawable.character_cowboy_single_blue
                "Pink" -> R.drawable.character_cowboy_single_pink
                "Yellow" -> R.drawable.character_cowboy_single_yellow
                "Purple" -> R.drawable.character_cowboy_single_purp
                "Orange" -> R.drawable.character_cowboy_single_orange
                else -> R.drawable.character_cowboy_single_green
            }
            "Duck" -> when (color) {
                "Blue" -> R.drawable.character_duck_still_blue
                "Pink" -> R.drawable.character_duck_still_pink
                "Yellow" -> R.drawable.character_duck_still_yellow
                "Purple" -> R.drawable.character_duck_still_purple
                "Orange" -> R.drawable.character_duck_still_orange
                else -> R.drawable.character_duck_still_green
            }
            "Headphones" -> when (color) {
                "Blue" -> R.drawable.base_headphone_still_blue
                "Pink" -> R.drawable.base_headphone_still_pink
                "Yellow" -> R.drawable.base_headphone_still_yellow
                "Purple" -> R.drawable.base_headphone_still_purple
                "Orange" -> R.drawable.base_headphone_still_orange
                else -> R.drawable.base_headphone_still_green
            }
            "Mushroom" -> when (color) {
                "Blue" -> R.drawable.character_mush_still_blue
                "Pink" -> R.drawable.character_mush_still_pink
                "Yellow" -> R.drawable.character_mush_still_yellow
                "Purple" -> R.drawable.character_mush_still_purple
                "Orange" -> R.drawable.character_mush_still_orange
                else -> R.drawable.character_mush_still_green
            }
            "Plant Hat" -> when (color) {
                "Blue" -> R.drawable.character_plant_still_blue
                "Pink" -> R.drawable.character_plant_still_pink
                "Yellow" -> R.drawable.character_plant_still_yellow
                "Purple" -> R.drawable.character_plant_still_purple
                "Orange" -> R.drawable.character_plant_still_orange
                else -> R.drawable.character_plant_still_green
            }
            else -> baseCharacterResource
        }
    }

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

    val accessoryImages = listOf(
        ShopItem(R.drawable.bow_accessories, 100, "Pink Bow"),
        ShopItem(R.drawable.cowboy_hat, 159, "Cowboy Hat"),
        ShopItem(R.drawable.duck_head, 200, "Duck"),
        ShopItem(R.drawable.headphones, 100, "Headphones"),
        ShopItem(R.drawable.mushroomt, 100, "Mushroom"),
        ShopItem(R.drawable.plant_hat, 200, "Plant Hat")
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Stage area in the middle (Mutually Exclusive Preview)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (showAccessories) {
                    // --- CHARACTER VIEW ---
                    SpriteCharacter(
                        drawableId = selectedAccessory ?: baseCharacterResource,
                        column = 0,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // --- PLANT VIEW ---
                    selectedPot?.let { potRes ->
                        AsyncImage(
                            model = potRes,
                            contentDescription = "Selected Pot",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    selectedFlower?.let { flowerRes ->
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
        }

        // Control buttons
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
                    if (showPlantPots) { showFlowers = false; showAccessories = false }
                },
                modifier = Modifier.height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showPlantPots) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Pots", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    showFlowers = !showFlowers
                    if (showFlowers) { showPlantPots = false; showAccessories = false }
                },
                modifier = Modifier.height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showFlowers) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Flowers", fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    showAccessories = !showAccessories
                    if (showAccessories) { showPlantPots = false; showFlowers = false }
                },
                modifier = Modifier.height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showAccessories) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = "Acc", fontSize = 12.sp)
            }
        }

        // Grid area at the bottom
        Box(modifier = Modifier.height(300.dp)) {
            val currentList = when {
                showPlantPots -> potImages
                showFlowers -> flowerImages
                showAccessories -> accessoryImages
                else -> emptyList()
            }

            if (currentList.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(currentList) { item ->
                        ShopGridItem(
                            item = item,
                            userCoins = coins,
                            onBuy = {
                                when {
                                    showPlantPots -> {
                                        gameViewModel.spendCoins(item.price)
                                        selectedPot = item.imageRes
                                    }
                                    showFlowers -> {
                                        gameViewModel.spendCoins(item.price)
                                        selectedFlower = item.imageRes
                                    }
                                    showAccessories -> {
                                        val correctSprite = getComposedSprite(item, currentColor)
                                        gameViewModel.spendCoins(item.price)
                                        gameViewModel.updateCharacterSprite(correctSprite)
                                        selectedAccessory = correctSprite
                                    }
                                }
                            }
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Select a category to shop", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ShopGridItem(item: ShopItem, userCoins: Int, onBuy: () -> Unit) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.imageRes,
                modifier = Modifier.size(60.dp),
                contentDescription = item.name
            )
            Text(text = item.name, fontSize = 10.sp, maxLines = 1)
            Text(text = "🪙 ${item.price}", fontSize = 12.sp, fontWeight = FontWeight.Bold)

            Button(
                onClick = onBuy,
                enabled = userCoins >= item.price,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Buy", fontSize = 10.sp)
            }
        }
    }
}
