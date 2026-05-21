package com.example.organivy.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.R
import com.example.organivy.data.GameState
import com.example.organivy.data.Header
import com.example.organivy.ui.subpages.garden.SpriteAnimation
import com.example.organivy.ui.theme.*
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GardenScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToMap: () -> Unit,
    gameViewModel: GameViewModel
) {

    val state2 = gameViewModel.uiState
    var isEditingName by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(state2.gardenName) }

    // Update tempName if state2.gardenName changes externally (e.g. from Firebase)
    LaunchedEffect(state2.gardenName) {
        if (!isEditingName) {
            tempName = state2.gardenName
        }
    }

//    var expanded by remember { mutableStateOf(false) }
//
//    Scaffold(
//        floatingActionButton = {
//            Column(
//                modifier = Modifier.wrapContentHeight(),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalAlignment = Alignment.End
//            ) {
//                // Expanded FABs
//                AnimatedVisibility(
//                    visible = expanded,
//                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
//                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
//                ) {
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(16.dp),
//                        horizontalAlignment = Alignment.End
//                    ) {
//                        FloatingActionButton(onClick = {
//                            expanded = false
//                            onNavigateToJournal()
//                        }) {
//                            Text(
//                                text = "Green Journal",
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                modifier = Modifier.padding(horizontal = 16.dp)
//                            )
//                        }
//                        FloatingActionButton(onClick = {
//                            expanded = false
//                            onNavigateToShop()
//                        }) {
//                            Text(
//                                text = "Shop",
//                                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                                modifier = Modifier.padding(horizontal = 16.dp)
//                            )
//                        }
//                    }
//                }
//
//                // Main toggle FAB
//                FloatingActionButton(onClick = { expanded = !expanded }) {
//                    Icon(
//                        imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
//                        contentDescription = "Expand/Collapse"
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->

        // i will be changing this down. i wonder if i should comment it out?
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //val photoViewModel = viewModel<PhotoViewModel>()
                //val gameViewModel = viewModel<GameViewModel>()
                //Header(photoViewModel = photoViewModel, gameViewModel = gameViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                // Garden Title
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Garden",
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Garden Name
                if (isEditingName) {
                    OutlinedTextField(
                        value = tempName,
                        onValueChange = { tempName = it },
                        modifier = Modifier.padding(vertical = 8.dp),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                if (tempName.isNotBlank()) {
                                    gameViewModel.updateGardenName(tempName)
                                }
                                isEditingName = false
                            }) {
                                Icon(Icons.Default.Check, contentDescription = "Save Garden Name")
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = CircleShape
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .combinedClickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { },
                                onDoubleClick = { isEditingName = true }
                            )
                    ) {
                        Text(
                            text = state2.gardenName,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))


                //plant
                Box{
                    Image(
                        painter = painterResource(id = R.drawable.garden_official),
                        contentDescription = "Garden",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Box(modifier = Modifier.fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .padding(0.dp, 10.dp),
                        ){
                        PlantSection(uiState = state2)
                    }




                }


                Spacer(modifier = Modifier.width(16.dp))




                // Plant Grid
                //PlantGrid()

                /*
                Button(onClick = onNavigateToMap ) {
                    Text(" OrganIvy World Map")
                }*/

                //SpriteAnimation()
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp, top = 250.dp),
                horizontalAlignment = Alignment.End
            ) {


                // item 1
                Box(modifier = Modifier
                    .size(100.dp)
                    .clickable { onNavigateToJournal() }) {


                    Image(
                        painter = painterResource(id = R.drawable.card),
                        contentDescription = "card",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit,
                        //filterQuality = FilterQuality.None

                    )

                    Text(
                        text = "\uD83D\uDCD6 ",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp
                    )

                }


                Spacer(modifier = Modifier.height(20.dp))


                //item 2 shop
                Box(modifier = Modifier.clickable { onNavigateToShop() }) {


                    Image(
                        painter = painterResource(id = R.drawable.card),
                        contentDescription = "Garden Image",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit,
                        //filterQuality = FilterQuality.None

                    )

                    Text(
                        text = "\uD83D\uDED2 ",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp
                    )
                }


            }

        }

}

// under garden
fun getPlantImage(level: Int): Int {
    return when(level) {

        0 -> R.drawable.seeds_falling_single

        1 -> R.drawable.plant_budding_photo

        2 -> R.drawable.daisy_blooming_part_three

        3 -> R.drawable.daisy_blooming_final_part_three

        else -> R.drawable.seeds_falling
    }
}

@Composable
fun PlantSection(
    uiState: GameState
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = getPlantImage(uiState.plantLevel)
            ),
            contentDescription = "Plant",
            modifier = Modifier.size(220.dp)
        )

        Text(
            text = "Plant Level ${uiState.plantLevel}"
        )
    }
}




@Composable
fun StatBar(icon: ImageVector, progress: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}

@Composable
fun CategoryButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun PlantGrid() {
    Column {
        repeat(3) { // 3 rows
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) { // 4 items per row
                    PlantItem()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PlantItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
        )
        Text(
            text = "Fra...",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewGardenScreen() { GardenScreen(onNavigateToProfile = {},onNavigateToShop = {},
    onNavigateToJournal = {}, onNavigateToStats ={}, onNavigateToMap ={}
)


}*/