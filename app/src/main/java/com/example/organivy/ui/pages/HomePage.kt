package com.example.organivy.ui.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.R
import com.example.organivy.data.Header
import com.example.organivy.data.StatBar
import com.example.organivy.ui.components.ChallengeItem
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToBadges: () -> Unit,
    onNavigateToStatsandImpact: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToGarden: () -> Unit,
    photoViewModel: PhotoViewModel,
    gameViewModel: GameViewModel,
) {

    val state = photoViewModel.uiState
    val state2 = gameViewModel.uiState


    //val photoViewModel: PhotoViewModel = viewModel()
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
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        FloatingActionButton(onClick = {
                            expanded = false
                            onNavigateToStatsandImpact()
                        }) {
                            Text(
                                text = "Stats and Impact",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                            //fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }




                //PLANT PREVIEW(CLICKABLE)
                item {

                    Box (
                        modifier = Modifier
                            .size(280.dp)
                            .clickable { onNavigateToGarden() }
                    ){
                       /* Image(
                            painter = painterResource(id = R.drawable.wooden_border),
                            contentDescription = "Garden Image",
                            modifier = Modifier.size(280.dp),
                            contentScale = ContentScale.Fit,
                            //filterQuality = FilterQuality.None

                        )*/


                        Card(
                            //onClick = onNavigateToGarden,
                            modifier = Modifier
                                .size(270.dp)
                                .align(alignment = Alignment.Center)
                                .padding(25.dp),

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

                                Image(
                                    painter = painterResource(id = R.drawable.garden_temp),
                                    contentDescription = "Garden",
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.BottomCenter)
                                        .padding(0.dp, 10.dp),
                                ) {
                                    PlantSection(uiState = state2)
                                }


                            }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
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
                    .clickable { onNavigateToBadges() }) {


                    Image(
                        painter = painterResource(id = R.drawable.card),
                        contentDescription = "card",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit,
                        //filterQuality = FilterQuality.None

                    )

                    Text(
                        text = "\uD83C\uDFC5 ",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp
                    )

                }


                Spacer(modifier = Modifier.height(20.dp))


                //item 2
                Box(modifier = Modifier.clickable { onNavigateToBadges() }) {


                    Image(
                        painter = painterResource(id = R.drawable.card),
                        contentDescription = "Garden Image",
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










            }




















        }


    }
}

/*
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
*/