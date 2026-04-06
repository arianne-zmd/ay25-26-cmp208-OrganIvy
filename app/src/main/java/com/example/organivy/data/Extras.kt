package com.example.organivy.data

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.organivy.ui.pages.header
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel


/*
TABLE OF CONTENTS
1. checkboxes
2. grid item
3. box extras( header, delete button
 */




// CHECKBOXESSSSSSSSSSSSSSSSSSSSSSSSSSSSS
@Composable
fun PicCheckbox(photo: Photo,viewModel: PhotoViewModel){

    // Observe ViewModel state
    val uiState = viewModel.uiState

    // Determine if this photo is selected
    val isChecked = uiState.deletionList.contains(photo)



    Row(
        modifier = Modifier.fillMaxWidth() .padding(4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                Log.d("PhotoTest", "Checkbox CLICKED") // 👈 test first

                viewModel.onPhotoChecked(photo, checked)

                Log.d("PhotoTest", "Photo: ${photo.id}")
                Log.d("PhotoTest", "List: ${viewModel.uiState.deletionList.map { it.id }}")
            }
        )
        //Text(text= info.text)
    }


}
//CHECKBOXESSSSSSSSSSSSSSSSSSSSSSSSSSSSSS

// GRID ITEM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
// GRID ITEM~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// BOX EXTRASSSSSSS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun BoxScope.BoxExtras ( photoViewModel: PhotoViewModel, gameViewModel: GameViewModel){



    val state = photoViewModel.uiState
    val state2 = gameViewModel.uiState

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        //.align(alignment = Alignment.TopEnd),
        color = MaterialTheme.colorScheme.background,

        ) {


        Column(modifier = Modifier.padding(20.dp)) {

            Spacer(modifier = Modifier.height(10.dp))

            header(photoviewModel = photoViewModel, gameviewModel = gameViewModel)

            Spacer(modifier = Modifier.height(15.dp))



        }

    }


    val context = LocalContext.current

    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            photoViewModel.clearDeletionList()
        }
    }

    val safeDeletion = remember { SafeDeletion(context) }


    Button(
        onClick = { val currentList = photoViewModel.uiState.deletionList

            Log.d("DeleteTest", "Button clicked with ${currentList.size}")
            Log.d("DeleteTest", "List: ${currentList.map { it.id }}")


            Log.d("VM_TEST", photoViewModel.toString())
            Log.d("DeleteTest", "Button  222 clicked with ${photoViewModel.uiState.deletionList.size}")
            if (currentList.isEmpty()) {
                Toast.makeText(
                    context,
                    "No photos selected for deletion",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                photoViewModel.deleteSelectedPhotos(
                    safeDeletion = safeDeletion,
                    launcher = deleteLauncher
                )
            }   },
        modifier = Modifier
            .padding(20.dp)
            .size(200.dp,60.dp)
            .align(alignment = Alignment.BottomEnd)

    ) {
        Text(
            text = "Delete",
            Modifier.padding(10.dp),
            fontSize = (16.sp)
        )

    }
}
// BOX EXTRASSSS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~