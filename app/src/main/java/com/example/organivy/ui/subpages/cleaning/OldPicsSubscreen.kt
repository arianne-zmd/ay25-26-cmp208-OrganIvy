package com.example.organivy.ui.subpages.cleaning

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.organivy.ui.pages.header
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.Photo
import com.example.organivy.data.PhotoState
import com.example.organivy.viewmodel.PhotoViewModel

@Composable
fun OldPicsSubscreen(onNavigateToProfile: () -> Unit,
                     viewModel: PhotoViewModel?

){

    val state = viewModel?.uiState





    if (state != null && state.oldPicsList.isNotEmpty()) {
            LazyGridScreen(viewModel = viewModel)
        } else {
            Text(
                text = "Loading photos...",
                color = MaterialTheme.colorScheme.onBackground
            )
        }


}

@Composable
fun LazyGridScreen(viewModel: PhotoViewModel) {
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
                text = "Old Images",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item{Spacer(modifier = Modifier.height(100.dp))}

        item{Spacer(modifier = Modifier.height(100.dp))}

        items(state.oldPicsList) {photo ->

            GridItem(photo = photo, viewModel = viewModel)
            Spacer(modifier = Modifier.height(8.dp))

        }
    }


}

@Composable
fun GridItem(photo: Photo, viewModel: PhotoViewModel) {

    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(300.dp)
            .width(200.dp),
        //horizontalAlignment = Alignment.CenterHorizontally
        horizontalAlignment = Alignment.End

    ){

         val uri = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
             photo.id)



        PicCheckbox(photo = photo, viewModel = viewModel )

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(30.dp)),
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(text = photo.name ,
            fontWeight = FontWeight.SemiBold
        )





    }


}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOldPicsSubscreen() {
    OldPicsSubscreen(onNavigateToProfile = {}, viewModel = null)
}