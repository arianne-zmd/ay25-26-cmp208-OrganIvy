package com.example.organivy.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.R
import com.example.organivy.ui.components.LayeredCharacter
import com.example.organivy.ui.components.SpriteLayer
import com.example.organivy.viewmodel.GameViewModel

@Composable
fun OnboardingScreen(
    gameViewModel: GameViewModel,
    onFinishOnboarding: () -> Unit
) {
    val uiState = gameViewModel.uiState



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Your Avatar",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Customize your look before we start!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Character Preview
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFB2A8FF).copy(alpha = 0.2f), RoundedCornerShape(100.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

        }

        Spacer(modifier = Modifier.height(40.dp))

        // Skin Tone Selector
        Text(
            text = "Skin Tone",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            /* items(baseOptions) { resId ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (uiState.characterSprite == resId) 3.dp else 1.dp,
                            color = if (uiState.characterSprite== resId) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable { gameViewModel.updateCharacterSprite(resId) }
                        .padding(8.dp)
                )

                {

                    SpriteLayer(drawableId = resId, column = 0)
                }
            }

             */
        }

        Spacer(modifier = Modifier.height(24.dp))



        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinishOnboarding,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Finish & Start Journey", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
