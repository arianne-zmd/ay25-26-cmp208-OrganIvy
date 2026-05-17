package com.example.organivy.ui.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.ui.theme.backgroundLight
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import com.example.organivy.viewmodel.GameViewModel


@Composable
fun GreenJournalScreen(onNavigateToProfile: () -> Unit,
                       gameViewModel: GameViewModel) {

    val context = LocalContext.current


    LazyColumn {

        items(gameViewModel.uiState.unlockedEcoFacts) { fact ->

            Card(
                modifier = Modifier.padding(12.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(text = fact.title)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = fact.fact)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "🌱 Read more...",
                        color = androidx.compose.ui.graphics.Color(0xFF2E7D32),
                        modifier = Modifier.clickable {

                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(fact.source)
                            )

                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }





            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Green Journal",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

}