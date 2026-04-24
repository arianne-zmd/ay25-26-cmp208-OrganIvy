package com.example.organivy.ui.pages

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.copy
import com.example.organivy.data.Header
import com.example.organivy.data.StatBar
import com.example.organivy.ui.components.ChallengeItem
import com.example.organivy.ui.theme.textLight
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun HomeScreen(
    gameViewModel: GameViewModel,
    onNavigateToProfile: () -> Unit,
    onNavigateToBadges: () -> Unit,
    onNavigateToStatsandImpact: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToGarden: () -> Unit
) {
    val photoViewModel: PhotoViewModel = viewModel()
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
                            onNavigateToBadges()
                        }) {
                            Text(
                                text = "Badges",
                                color = textLight,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        FloatingActionButton(onClick = {
                            expanded = false
                            onNavigateToStatsandImpact()
                        }) {
                            Text(
                                text = "Stats and Impact",
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
        Box {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {

            // HEADING
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Header(photoViewModel = photoViewModel, gameViewModel = gameViewModel)

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome Back!",
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }


            //PLANT PREVIEW(CLICKABLE)
            item {
                Card(
                    onClick = onNavigateToGarden,
                    modifier = Modifier
                        .size(250.dp)
                        .padding(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                ) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "\uD83C\uDF3F Plant preview",
                            Modifier.padding(start = 20.dp)
                        )
                    }
                }
            }


            // CHALLENGES FOR THE WEEK
            item {
                var isExpanded by remember { mutableStateOf(false) }
                val challenges = gameViewModel.uiState.challenges
                val completedCount = challenges.count { it.isCompleted }

                Card(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = " ✨ Challenge for this week",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Done: $completedCount/${challenges.size}",
                                fontSize = 12.sp
                            )
                        }

                        // Progress Bar for challenges
                        StatBar(
                            label = "Progress",
                            current = completedCount,
                            max = challenges.size,
                            color = Color(0xFF9C27B0)
                        )

                        AnimatedVisibility(visible = isExpanded) {
                            Column(modifier = Modifier.padding(top = 16.dp)) {
                                challenges.forEach { challenge ->
                                    ChallengeItem(
                                        task = "${challenge.description} (${challenge.currentValue}/${challenge.targetValue})",
                                        isDone = challenge.isCompleted
                                    )
                                }
                            }
                        }
                    }
                }
            }


//            item{
//                Surface(
//                    modifier = Modifier.padding(vertical = 16.dp),
//                    shape = RoundedCornerShape(50),
//                    color = MaterialTheme.colorScheme.secondaryContainer
//                ) {
//                    Text(
//                        text = "boo",
//                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
//                        fontSize = 14.sp
//                    )
//                }
//            }


            /*
            item{
                Spacer(modifier = Modifier.height(40.dp))
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Badges Card - Left Aligned, 75% width
                    Box(
                        modifier = Modifier.fillMaxWidth()


                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(80.dp)
                                .align(Alignment.CenterStart),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        ) {
                            Box(
                                Modifier.fillMaxSize()
//                                .background(
//                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
//                                    shape = RoundedCornerShape(20.dp)
//                                )
                                , contentAlignment = Alignment.CenterStart
                            ) {
                                Text(text = "Badges Earning", Modifier.padding(start = 20.dp))
                            }
                        }
                    }

                    // Facts Card - Right Aligned, 75% width
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(80.dp)
                                .align(Alignment.CenterEnd),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        ) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                                Text(text = "Facts Learned", Modifier.padding(start = 20.dp))
                            }
                        }
                    }

                    // Photos Deleted Card - Left Aligned, 75% width
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(80.dp)
                                .align(Alignment.CenterStart),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        ) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                                Text(text = "Photos Deleted", Modifier.padding(start = 20.dp))
                            }
                        }
                    }
                }
            }

            item {
                // Carbon Footprint Card - Right Aligned, 75% width
                Box(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(80.dp)
                            .align(Alignment.CenterEnd),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                            Text(text = "Carbon Footprint ", Modifier.padding(start = 20.dp))
                        }
                    }
                }
            }

             */
        }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 10.dp, top = 250.dp),
                horizontalAlignment = Alignment.End
            ) {

                Box(modifier = Modifier.shadow(50.dp, shape = RoundedCornerShape(30.dp))){

                    Surface(
                        modifier = Modifier.padding(10.dp)
                            .size(80.dp)
                        ,
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp),




                        ){}

                    Surface(
                        modifier = Modifier.padding(10.dp)
                            .align(alignment = Alignment.Center)
                            .size(75.dp)
                        ,
                        shape = RoundedCornerShape(30),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "\uD83C\uDFC5",
                            modifier = Modifier.padding(14.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 40.sp
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.shadow(50.dp, shape = RoundedCornerShape(30.dp))){

                    Surface(
                        modifier = Modifier.padding(10.dp)
                            .size(80.dp)
                        ,
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp),


                        ){}

                    Surface(
                        modifier = Modifier.padding(10.dp)
                            .align(alignment = Alignment.Center)
                            .size(75.dp)
                        ,
                        shape = RoundedCornerShape(30),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "\uD83D\uDCD6 you ",
                            modifier = Modifier.padding(14.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 40.sp
                        )
                    }

                }


            }










    }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        gameViewModel = viewModel(),
        onNavigateToProfile = {},
        onNavigateToBadges = {},
        onNavigateToStatsandImpact = {},
        onNavigateToShop = {},
        onNavigateToJournal = {},
        onNavigateToGarden = {}
    )
}
