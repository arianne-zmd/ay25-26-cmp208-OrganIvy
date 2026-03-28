package com.example.organivy.ui.pages


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShopScreen(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Shop")


        PlantGrid()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShopScreen() { ShopScreen(onNavigateToProfile = {}
)


}