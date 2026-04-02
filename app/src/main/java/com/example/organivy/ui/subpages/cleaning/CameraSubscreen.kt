package com.example.organivy.ui.subpages.cleaning



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.Photo
import com.example.organivy.data.PhotoState
import com.example.organivy.ui.pages.header
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun CameraSubscreen(onNavigateToProfile: () -> Unit,
                    viewModel: PhotoViewModel?
                   ){

    val state = viewModel?.uiState


    if (state != null && state.cameraPicsList.isNotEmpty()) {
        LazyGridScreenCP(viewModel = viewModel)
    } else {
        Text(
            text = "Loading photos...",
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
fun LazyGridScreenCP(viewModel: PhotoViewModel) {
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

        items(state.cameraPicsList) {photo ->


            GridItem(photo = photo, viewModel = viewModel)
            Spacer(modifier = Modifier.height(8.dp))

        }
    }


}


// CHECKBOXESSSSSSSSSSSSSSSSSSSSSSSSSSSSS


@Composable
fun PicCheckbox(photo: Photo,viewModel: PhotoViewModel){

    // Observe ViewModel state
    val uiState = viewModel.uiState

    // Determine if this photo is selected
    val isChecked = uiState.deletionList.contains(photo)



        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    checked ->
                    // Tell ViewModel to update deletionList
                    viewModel.onPhotoChecked(photo, checked)




                }

            )
            //Text(text= info.text)
        }


}




//CHECKBOXESSSSSSSSSSSSSSSSSSSSSSSSSSSSSS


















// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCameraSubscreen() {
    CameraSubscreen(onNavigateToProfile = {}, viewModel = null)
}