package com.example.organivy.ui.subpages.cleaning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.ui.pages.header
import com.example.organivy.viewmodel.PhotoViewModel


@Composable
fun DownloadsSubscreen(onNavigateToProfile: () -> Unit,
                       viewModel: PhotoViewModel?
){

    val state = viewModel?.uiState


    if (state != null && state.downloadsList.isNotEmpty()) {
        LazyGridScreenDP(viewModel = viewModel)
    } else {
        Text(
            text = "Loading photos...",
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
fun LazyGridScreenDP(viewModel: PhotoViewModel) {
    val state = viewModel.uiState  //: PhotoState()



    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        columns = GridCells.Fixed( 2)
    ){


        item(span = { GridItemSpan(2) }){
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Full-width header
        item(span = { GridItemSpan(2) }) {
            header()
        }

        item(span = { GridItemSpan(2) }){
            Spacer(modifier = Modifier.height(10.dp))
        }

        item(span = {GridItemSpan(2)} ){
            Text(
                text = "Camera",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item{Spacer(modifier = Modifier.height(100.dp))}

        item{Spacer(modifier = Modifier.height(100.dp))}

        items(state.downloadsList) {photo ->

            GridItem(photo = photo)
            Spacer(modifier = Modifier.height(8.dp))

        }
    }


}

// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDownloadsSubscreen() {
    DownloadsSubscreen(onNavigateToProfile = {}, viewModel = null)
}