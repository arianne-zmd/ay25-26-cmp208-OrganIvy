package com.example.organivy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChallengeItem(task: String, isDone: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(56.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // The side box (green if done, orange if not)
        Box(
            modifier = Modifier
                .width(48.dp)
                .fillMaxHeight()
                .background(
                    if (isDone) Color(0xFF4CAF50) else Color(0xFFFFB74D),
                    RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White)
            } else {
                Text(text = "+", fontSize = 24.sp, color = Color.White)
            }
        }

        // The Task Text
        Text(
            text = task,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}
