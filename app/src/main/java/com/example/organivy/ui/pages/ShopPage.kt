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
import androidx.compose.material3.Divider
import coil.compose.AsyncImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun ShopScreen(onNavigateToProfile: () -> Unit) {
    var showPlantPots by remember { mutableStateOf(false) }
    var showFlowers by remember { mutableStateOf(false) }
    var selectedPot by remember { mutableStateOf<Int?>(null) }
    var selectedFlower by remember { mutableStateOf<Int?>(null) }

    val potImages = listOf(
        R.drawable.plantpot_1,
        R.drawable.plantpot_2,
        R.drawable.plantpot_3,
        R.drawable.plantpot_4,
        R.drawable.plantpot_5_1,
        R.drawable.plantpot_5_2,
        R.drawable.plantpot_5_3,
        R.drawable.plantpot_5_4,
        R.drawable.plantpot_6_1,
        R.drawable.plantpot_6_2,
        R.drawable.plantpot_6_3,
        R.drawable.plantpot_6_4,
        R.drawable.plantpot_6_5,
        R.drawable.plantpot_6_6,
        R.drawable.plantpot_6_7,
        R.drawable.plantpot_7_1,
        R.drawable.plantpot_7_2,
        R.drawable.plantpot_7_3,
        R.drawable.plantpot_7_5,
        R.drawable.plantpot_8
    )

    val flowerImages = listOf(
        R.drawable.cheap_daisy,
        R.drawable.cheap_daisy_2,
        R.drawable.cheap_lily,
        R.drawable.cheap_flower_1,
        R.drawable.cheap_flower_2,
        R.drawable.cheap_flower_3,
        R.drawable.cheap_rose,
        R.drawable.cheap_sunflower,
        R.drawable.expensive_babybreath,
        R.drawable.expensive_flower,
        R.drawable.expensive_flower_2,
        R.drawable.expensive_rose_1,
        R.drawable.expensive_sunflower,
        R.drawable.expensive_tulip_1,
        R.drawable.expensive_tulip_2,
        R.drawable.expensive_tulip_3,
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
                    AsyncImage(
                        model = flowerRes,
                        contentDescription = "Selected Flower",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.BottomCenter)
                            .offset(y = (-140).dp) // Adjust this to sit on the rim
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
            
            Divider(
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
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(potImages) { imageRes ->
                    AsyncImage(
                        model = imageRes,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable { selectedPot = imageRes }

                    )
                }
            }
        } else if (showFlowers) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(flowerImages) { imageRes ->
                    AsyncImage(
                        model = imageRes,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable { selectedFlower = imageRes }
                    )
                }
            }
        } else {
            // Placeholder spacer to keep buttons in the same position
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}
