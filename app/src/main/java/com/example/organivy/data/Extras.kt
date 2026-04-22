package com.example.organivy.data

import android.app.Activity
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.organivy.R
import com.example.organivy.ui.components.LayeredCharacter
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel


/*
TABLE OF CONTENTS
1. checkboxes
2. grid item
3. box extras( header, delete button)
4. header
5. stats bar
6. grid header
 */




// CHECKBOXES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun PicCheckbox(photo: Photo, photoViewModel: PhotoViewModel){

    // Observe ViewModel state
    val uiState = photoViewModel.uiState

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
                Log.d("PhotoTest", "Checkbox CLICKED")

                photoViewModel.onPhotoChecked(photo, checked)


                Log.d("PhotoTest", "Photo: ${photo.id}")
                Log.d("PhotoTest", "List: ${photoViewModel.uiState.deletionList.map { it.id }}")
            }
        )
        //Text(text= info.text)
    }


}
//CHECKBOXES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// GRID ITEM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun GridItem(photo: Photo, photoViewModel: PhotoViewModel) {

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



        PicCheckbox(photo = photo, photoViewModel = photoViewModel )

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
// GRID ITEM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// BOX EXTRAS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun BoxScope.BoxExtras ( photoViewModel: PhotoViewModel, gameViewModel: GameViewModel){

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        //.align(alignment = Alignment.TopEnd),
        color = MaterialTheme.colorScheme.background,

        ) {


        Column(modifier = Modifier.padding(20.dp)) {

            Spacer(modifier = Modifier.height(10.dp))

            GridHeader(photoViewModel = photoViewModel, gameViewModel = gameViewModel)

            Spacer(modifier = Modifier.height(15.dp))



        }

    }


    val context = LocalContext.current

    val deleteLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // ONLY Award coins and refresh if user confirmed the deletion. okkkkkkk
            val deletedCount = photoViewModel.uiState.deletionList.size
            gameViewModel.onDeletion(deletedCount)
            
            photoViewModel.clearDeletionList()
            photoViewModel.loadPhotos()
            
            Toast.makeText(context, "Photos deleted successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    val safeDeletion = remember { SafeDeletion(context) }


    Button(
        onClick = { 
            val currentList = photoViewModel.uiState.deletionList

            if (currentList.isEmpty()) {
                Toast.makeText(
                    context,
                    "No photos selected for deletion",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Launch the system deletion dialog
                photoViewModel.deleteSelectedPhotos(
                    safeDeletion = safeDeletion,
                    launcher = deleteLauncher
                )
            }   
        },
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
// BOX EXTRAS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// HEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun Header(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel){

    val state2 = gameViewModel.uiState

    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFB2A8FF), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                // USES THE CUSTOMIZED CHARACTER STATE
                LayeredCharacter(
                    baseId = state2.userBase,
                    hairId = state2.userHair,
                    outfitId = state2.userOutfit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(text = "Lvl. 4", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Stats Section
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "arianne donelly", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            StatBar(label = "Health", current = 50, max = 50, color = Color(0xFFFF5252))
            StatBar(label = "Experience", current = 16, max = 100, color = Color(0xFFFFD700))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        painter = painterResource(R.drawable.placeholder_icon),
                        contentDescription = null
                    )
                    Text(text = " CO\u2082 Saved", fontSize = 12.sp)
                }
                Text(text = "🪙 ${state2.coins}  💎 20", fontSize = 14.sp)
            }
        }
    }
}
// HEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


// STATBAR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun StatBar(label: String, current: Int, max: Int, color: Color){
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(text = "$current/$max",fontSize = 12.sp)
            Text (text = label, fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(5.dp))
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (max > 0) current.toFloat() / max.toFloat() else 0f)
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(5.dp)),
            )
        }
    }
}
// STATBAR ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// GRIDHEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun GridHeader(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel){

    val state2 = gameViewModel.uiState

    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Lvl. 4", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        painter = painterResource(R.drawable.placeholder_icon),
                        contentDescription = null
                    )
                    Text(text = " CO\u2082 Saved", fontSize = 12.sp)
                }
                Text(text = "🪙 ${state2.coins}  💎 20", fontSize = 14.sp)
            }

    }
}
// GRIDHEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~