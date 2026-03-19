package com.example.organivy.ui.pages

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.ui.theme.textLight

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToBadges: () -> Unit,
    onNavigateToStatsandImpact: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToJournal: () -> Unit
) {
    val context = LocalContext.current
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "  Welcome to OrganIvy User!",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 60.dp, 30.dp, 450.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Username", Modifier.padding(15.dp))
                Text(text = "Level", Modifier.padding(15.dp))
                Text(text = "Progress Bar", Modifier.padding(15.dp))
            }

            Card(
                modifier = Modifier
                    .padding(20.dp, 400.dp, 210.dp, 30.dp)
                    .size(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Badges Earning", Modifier.padding(20.dp))
            }

            Card(
                modifier = Modifier
                    .padding(210.dp, 400.dp, 20.dp, 30.dp)
                    .size(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Facts Learned", Modifier.padding(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        onNavigateToProfile = {},
        onNavigateToBadges = {},
        onNavigateToStatsandImpact = {},
        onNavigateToShop = {},
        onNavigateToJournal = {}
    )
}
