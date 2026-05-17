package com.example.organivy.data

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.organivy.ui.components.BottomNavigationBar
import com.example.organivy.ui.pages.BadgePage
import com.example.organivy.ui.pages.CleaningPage
import com.example.organivy.ui.pages.GardenScreen
import com.example.organivy.ui.pages.GreenJournalScreen
import com.example.organivy.ui.pages.HomeScreen
import com.example.organivy.ui.pages.SettingsScreen
import com.example.organivy.ui.pages.ShopScreen
import com.example.organivy.ui.pages.StatsandImpactPage
import com.example.organivy.ui.subpages.cleaning.BlurryPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.CameraSubscreen
import com.example.organivy.ui.subpages.cleaning.DownloadsSubscreen
import com.example.organivy.ui.subpages.cleaning.DuplicatedPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.LargePicsSubscreen
import com.example.organivy.ui.subpages.cleaning.OldPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.ScreenshotsSubscreen
import com.example.organivy.ui.subpages.cleaning.WhatsappImagesSubscreen
import com.example.organivy.ui.subpages.garden.WorldMapSubscreen

// This folder is for your API clients, repositories, and data sources.




/*
*
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
                    //.background(backgroundLight)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val photoViewModel = viewModel<PhotoViewModel>()
                val gameViewModel = viewModel<GameViewModel>()
                Header(photoViewModel = photoViewModel, gameViewModel = gameViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                // Garden Title pill
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

                // Garden Name pill
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

                // Image & Stats Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.base_garden),
                        contentDescription = "Garden Image",
                        modifier = Modifier
                            .weight(1.5f)
                            .aspectRatio(1.5f)
                            .shadow(8.dp, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Stats Column
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Stats",
                            fontWeight = FontWeight.Bold,
                            color = primaryLight,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        StatBar(icon = Icons.Filled.WaterDrop, progress = 0.7f)
                        StatBar(icon = Icons.Filled.WbSunny, progress = 0.4f)
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))

                // Plant Grid
                //PlantGrid()

                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = onNavigateToMap ) {
                    Text(" OrganIvy World Map")
                }
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
*/


















