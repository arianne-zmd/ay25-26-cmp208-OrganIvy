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
import com.example.organivy.ui.components.JumpingCharacter
import com.example.organivy.ui.components.SpriteLayer
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.data.auth.ColorOption

@Composable
fun OnboardingScreen(
    gameViewModel: GameViewModel,
    onFinishOnboarding: () -> Unit
) {
    val uiState = gameViewModel.uiState

    val colorOptions = listOf(
        ColorOption(R.drawable.character_base_single_green, "Green", Color(0xFF4CAF50)),
        ColorOption(R.drawable.character_base_single_blue, "Blue", Color(0xFF2196F3)),
        ColorOption(R.drawable.character_base_single_pink, "Pink", Color(0xFFE91E63)),
        ColorOption(R.drawable.character_base_single_orange, "Orange", Color(0xFFFF9800)),
        ColorOption(R.drawable.character_base_single_purple, "Purple", Color(0xFF9C27B0)),
        ColorOption(R.drawable.character_base_single_yellow, "Yellow", Color(0xFFFFEB3B))
    )

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
            if (uiState.characterColour.isNotEmpty()) {
                val selectedOption = colorOptions.find { it.name == uiState.characterColour }
                if (selectedOption != null) {
                    JumpingCharacter(
                        baseId = selectedOption.resId,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Text(
                    text = "?",
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Color Selector
        Text(
            text = "Pick Your Color",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(colorOptions) { option ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(option.color.copy(alpha = 0.2f))
                        .border(
                            width = if (uiState.characterColour == option.name) 3.dp else 1.dp,
                            color = if (uiState.characterColour == option.name) option.color else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            gameViewModel.updateCharacterColour(option.name, option.resId.toString())
                        },
                    contentAlignment = Alignment.Center
                ) {
                    LayeredCharacter(
                        baseId = option.resId,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinishOnboarding,
            enabled = uiState.characterColour.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Finish & Start Journey", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
