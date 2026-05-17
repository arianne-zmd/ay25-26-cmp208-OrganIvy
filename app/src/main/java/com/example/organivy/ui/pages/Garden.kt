package com.example.organivy.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.organivy.data.Header
import com.example.organivy.ui.subpages.garden.SpriteAnimation
import com.example.organivy.ui.theme.*
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun GardenScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToMap: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Expanded FABs
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        FloatingActionButton(onClick = {
                            expanded = false
                            onNavigateToJournal()
                        }) {
                            Text(
                                text = "Green Journal",
                                color = textLight,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        FloatingActionButton(onClick = {
                            expanded = false
                            onNavigateToShop()
                        }) {
                            Text(
                                text = "Shop",
                                color = textLight,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                // Main toggle FAB
                FloatingActionButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = "Expand/Collapse"
                    )
                }
            }
        }
    ) { innerPadding ->

        // i will be changing this down. i wonder if i should comment it out?
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    color = primaryContainerLight,
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
                        color = primaryLight
                    )
                }

                // Garden Name
                Surface(
                    shape = CircleShape,
                    color = secondaryContainerLight,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Garden Name",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = primaryLight
                    )
                }


                Spacer(modifier = Modifier.height(20.dp))


                Box{
                    Image(
                        painter = painterResource(id = R.drawable.garden_temp),
                        contentDescription = "Garden",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Box(modifier = Modifier.fillMaxWidth()
                        .align(alignment = Alignment.BottomCenter)
                        .padding(0.dp, 20.dp),
                        ){
                        SpriteAnimation()
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
        } // all of thing can be in a lazy colunm ^




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
            tint = primaryLight
        )
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            color = primaryLight,
            trackColor = secondaryContainerLight,
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
            color = primaryLight,
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
                .background(secondaryContainerLight, RoundedCornerShape(8.dp))
        )
        Text(
            text = "Fra...",
            fontSize = 10.sp,
            color = outlineLight,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewGardenScreen() { GardenScreen(onNavigateToProfile = {},onNavigateToShop = {},
    onNavigateToJournal = {}, onNavigateToStats ={}, onNavigateToMap ={}
)


}