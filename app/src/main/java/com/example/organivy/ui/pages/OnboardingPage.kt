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

    // Options for customization
    val baseOptions = listOf(
        R.drawable.char_base_shade1, R.drawable.char_base_shade2, R.drawable.char_base_shade3,
        R.drawable.char_base_shade4, R.drawable.char_base_shade5, R.drawable.char_base_shade6,
        R.drawable.char_base_shade7, R.drawable.char_base_shade8, R.drawable.char_base_shade9,
        R.drawable.char_base_shade10, R.drawable.char_base_shade11
    )

    val hairOptions = listOf(
        R.drawable.char_hair_bob1, R.drawable.char_hair_bob2, R.drawable.char_hair_bob3,
        R.drawable.char_hair_bob4, R.drawable.char_hair_bob5, R.drawable.char_hair_bob6,
        R.drawable.char_hair_bob7, R.drawable.char_hair_bob8, R.drawable.char_hair_bob9,
        R.drawable.char_hair_bob10, R.drawable.char_hair_bob11, R.drawable.char_hair_bob12,
        R.drawable.char_hair_bob13, R.drawable.char_hair_bob14,
        R.drawable.char_hair_dap1, R.drawable.char_hair_dap2, R.drawable.char_hair_dap3,
        R.drawable.char_hair_dap4, R.drawable.char_hair_dap5, R.drawable.char_hair_dap6,
        R.drawable.char_hair_dap7, R.drawable.char_hair_dap8, R.drawable.char_hair_dap9,
        R.drawable.char_hair_dap10, R.drawable.char_hair_dap11, R.drawable.char_hair_dap12,
        R.drawable.char_hair_dap13, R.drawable.char_hair_dap14
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
            LayeredCharacter(
                baseId = uiState.userBase,
                hairId = uiState.userHair,
                outfitId = uiState.userOutfit,
                modifier = Modifier.fillMaxSize()
            )
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
            items(baseOptions) { resId ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (uiState.userBase == resId) 3.dp else 1.dp,
                            color = if (uiState.userBase == resId) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable { gameViewModel.updateBase(resId) }
                        .padding(8.dp)
                ) {
                    SpriteLayer(drawableId = resId, column = 0)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hair Selector
        Text(
            text = "Hair Style",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(hairOptions) { resId ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (uiState.userHair == resId) 3.dp else 1.dp,
                            color = if (uiState.userHair == resId) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .clickable { gameViewModel.updateHair(resId) }
                        .padding(8.dp)
                ) {
                    SpriteLayer(drawableId = resId, column = 0)
                }
            }
        }

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
