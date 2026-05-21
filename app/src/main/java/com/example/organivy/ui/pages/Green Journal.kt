package com.example.organivy.ui.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.R
import com.example.organivy.data.EcoFact
import com.example.organivy.data.allEcoFacts
import com.example.organivy.viewmodel.GameViewModel

@Composable
fun GreenJournalScreen(
    onNavigateToProfile: () -> Unit,
    gameViewModel: GameViewModel
) {
    val uiState = gameViewModel.uiState
    val unlockedFacts = uiState.unlockedEcoFacts
    val totalFactsCount = allEcoFacts.size
    val unlockedCount = unlockedFacts.size

    val factsByCategory = remember(allEcoFacts) {
        allEcoFacts.groupBy { it.title }
    }

    var expandedFact by remember { mutableStateOf<EcoFact?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Title Pill
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Green Journal",
                        modifier = Modifier.padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Pill
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        text = "$unlockedCount/$totalFactsCount",
                        modifier = Modifier.padding(vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Categories List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    factsByCategory.forEach { (category, facts) ->
                        item {
                            val earnedInCategory = facts.count { fact -> unlockedFacts.any { it.id == fact.id } }
                            CategorySection(
                                name = category,
                                earned = earnedInCategory,
                                total = facts.size,
                                facts = facts,
                                unlockedFacts = unlockedFacts,
                                onFactClick = { fact -> expandedFact = fact }
                            )
                        }
                    }
                }
            }

            // Expanded Version Overlay
            expandedFact?.let { fact ->
                EcoFactDialog(
                    fact = fact,
                    onDismiss = { expandedFact = null }
                )
            }
        }
    }
}

@Composable
fun CategorySection(
    name: String,
    earned: Int,
    total: Int,
    facts: List<EcoFact>,
    unlockedFacts: List<EcoFact>,
    onFactClick: (EcoFact) -> Unit
) {
    Column {
        Text(
            text = "$name ($earned/$total earned)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(facts) { fact ->
                        val isUnlocked = unlockedFacts.any { it.id == fact.id }
                        FactCard(
                            fact = fact, 
                            isUnlocked = isUnlocked,
                            onLongClick = { onFactClick(fact) }
                        )
                    }
                }

                // Right Arrow
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FactCard(fact: EcoFact, isUnlocked: Boolean, onLongClick: () -> Unit) {
    val context = LocalContext.current
    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .size(140.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .combinedClickable(
                enabled = isUnlocked,
                onClick = { rotated = !rotated },
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (rotation <= 90f) {
            // Front Side
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (fact.imageRes != 0) {
                    Image(
                        painter = painterResource(id = fact.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().alpha(if (isUnlocked) 1f else 0.3f),
                        contentScale = ContentScale.Crop
                    )
                }

                if (!isUnlocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                } else if (!rotated) {
                    // Small hint that it can be flipped
                    Text(
                        text = "Tap to flip\nLong tap to expand",
                        fontSize = 10.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }
        } else {
            // Back Side
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = fact.fact,
                        fontSize = 11.sp,
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Source ↗",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            if (fact.source.isNotEmpty()) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fact.source))
                                context.startActivity(intent)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EcoFactDialog(fact: EcoFact, onDismiss: () -> Unit) {
    val context = LocalContext.current
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = {
            Text(
                text = fact.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (fact.imageRes != 0) {
                    Image(
                        painter = painterResource(id = fact.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                Text(
                    text = fact.fact,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                
                if (fact.source.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "View Source",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fact.source))
                            context.startActivity(intent)
                        }
                    )
                }
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
