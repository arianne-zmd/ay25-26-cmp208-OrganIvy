package com.example.organivy.ui.subpages.cleaning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.ui.pages.header

@Composable
fun BlurryPicsSubscreen(onNavigateToProfile: () -> Unit,
){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp, ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        //verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item{ header() }

        item{Text(
            text = "Blurry",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )}
    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBlurryPicsSubscreen() {
    BlurryPicsSubscreen(onNavigateToProfile = {})
}