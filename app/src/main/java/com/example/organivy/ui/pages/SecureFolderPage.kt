package com.example.organivy.ui.pages


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.data.BoxExtras
import com.example.organivy.data.GridItem
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun SecureFolderPage(onNavigateToProfile: () -> Unit,
                    photoViewModel: PhotoViewModel?,
                    gameViewModel: GameViewModel
){

    val state = photoViewModel?.uiState



    if (state != null && state.cameraPicsList.isNotEmpty()) {
        LazyGridScreenSecureFP(photoViewModel = photoViewModel,  gameViewModel = gameViewModel)
    } else {
        Text(
            text = "Loading photos...",
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
fun LazyGridScreenSecureFP(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel ) {
    val state = photoViewModel.uiState  //: PhotoState()


    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            columns = GridCells.Fixed( 2)
        )
        {


            item(span = { GridItemSpan(2) }){
                Spacer(modifier = Modifier.height(20.dp))
            }


            item{Spacer(modifier = Modifier.height(100.dp))}

            item{Spacer(modifier = Modifier.height(100.dp))}

            items(state.secureFolderList) {photo ->


                GridItem(photo = photo, photoViewModel = photoViewModel)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
        // under grid


        BoxExtras(photoViewModel, gameViewModel)

        Column{
            Text(
                text = "Secure Folder has ${state.secureFolderList.size} images",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 80.dp)

            )

            Text(
                text = "Deleting ${state.deletionList.size} images",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 2.dp)

            )
        }



    }


}

