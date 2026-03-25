package com.example.organivy.ui.subpages.cleaning

import android.content.ContentUris
import android.provider.MediaStore
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.organivy.data.Photo
import com.example.organivy.ui.pages.CleaningPage

@Composable
fun CameraSubscreen(onNavigateToProfile: () -> Unit,
                   ){

    // photos: List<Photo>
//    val uri = ContentUris.withAppendedId(
//        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//        photo.id
//    )
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize()
//            .padding(20.dp, ),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start,
//        //verticalArrangement = Arrangement.spacedBy(16.dp)
//    ){
//        item{Text(
//            text = "camera",
//            fontSize = 20.sp,
//            color = MaterialTheme.colorScheme.onBackground
//        )}
//
////        item{
////            AsyncImage(
////                model = uri,
////                contentDescription = null
////            )
////        }
//
//    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
//        items(photos) { photo ->
//            AsyncImage(
//                model = uri,
//                contentDescription = null
//            )
//        }
    }

}

// Preview function goes outside MainActivity class
@Preview(showBackground = true)
@Composable
fun PreviewCameraSubscreen() {
    CameraSubscreen(onNavigateToProfile = {},) //photo = Photo(id = 12, dateAdded = 10000000, size = 10000, dateTaken = 2333300, name ="boo", path = "sthhry", height = 20, width = 20  ))
}