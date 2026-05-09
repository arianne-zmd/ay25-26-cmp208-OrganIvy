package com.example.organivy.ui.subpages.cleaning

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.BoxExtras
import com.example.organivy.data.GridItem
import com.example.organivy.viewmodel.FirebaseViewModel
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel


@Composable
fun WhatsappImagesSubscreen(onNavigateToProfile: () -> Unit,
                            photoViewModel: PhotoViewModel?,
                            gameViewModel: GameViewModel,
                            firebaseViewModel: FirebaseViewModel
){

    val state =photoViewModel?.uiState


    if (state != null && state.whatsappPicsList.isNotEmpty()) {
        LazyGridScreenWAP(photoViewModel = photoViewModel, gameViewModel = gameViewModel, firebaseViewModel = firebaseViewModel)
    } else {
        Text(
            text = "Loading photos...",
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
fun LazyGridScreenWAP(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel, firebaseViewModel: FirebaseViewModel) {
    val state = photoViewModel.uiState  //: PhotoState()


    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            columns = GridCells.Fixed(2)
        ) {

            item(span = { GridItemSpan(2) }){
                Spacer(modifier = Modifier.height(20.dp))
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }

            item { Spacer(modifier = Modifier.height(100.dp)) }

            val shownPhotos = state.whatsappPicsList.filter{
                it !in state.secureFolderList
            }
            items(shownPhotos) { photo ->

                GridItem(photo = photo, photoViewModel = photoViewModel, firebaseViewModel = firebaseViewModel)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
        // under grid

        //val photoViewModel = viewModel<PhotoViewModel>()
        //val gameViewModel = viewModel<GameViewModel>()
        BoxExtras(photoViewModel, gameViewModel)

        Text(
            text = "Deleting ${state.deletionList.size} images",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 80.dp)

        )
    }


}

// Preview function goes outside MainActivity class
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewWhatsappImageSubscreen() {
//    WhatsappImagesSubscreen(onNavigateToProfile = {}, photoViewModel = null)
//}