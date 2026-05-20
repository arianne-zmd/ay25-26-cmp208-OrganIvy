package com.example.organivy.data

import android.app.Activity
import android.app.Application
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.organivy.ui.pages.StatBar


import com.example.organivy.ui.components.LayeredCharacter
import com.example.organivy.ui.components.JumpingCharacter
import com.example.organivy.viewmodel.FirebaseViewModel
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel


/*
TABLE OF CONTENTS
1. checkboxes ( piccheckboxes   & securepiccheckboxes )
2. grid item
3. box extras( header, delete button)
4. header
5. stat bar
6. grid header
 */




// CHECKBOXES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// PicCheckbox ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun PicCheckbox(photo: Photo, photoViewModel: PhotoViewModel){

    // Observe ViewModel state
    val uiState = photoViewModel.uiState

    // Determine if this photo is selected
    val isChecked = uiState.deletionList.contains(photo)



    Row(
        modifier = Modifier.padding(4.dp),
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
// PicCheckbox ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


// SecurePicCheckbox ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun SecurePicCheckbox(photo: Photo, photoViewModel: PhotoViewModel, firebaseViewModel: FirebaseViewModel){

    // Observe ViewModel state
    val uiState = photoViewModel.uiState

    // Determine if this photo is selected
    val isLiked = uiState.secureFolderList.contains(photo)



    Row(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        IconButton(
            onClick = {

                val newState = !isLiked

                Log.d("PhotoTest", "Like button CLICKED")

                photoViewModel.onSecurePhotoChecked(photo, newState, firebaseViewModel)

                Log.d("PhotoTest", "Photo: ${photo.id}")
                Log.d(
                    "PhotoTest",
                    "List: ${photoViewModel.uiState.secureFolderList.map { it.id }}"
                )
            }
        ) {

            Icon(
                imageVector =
                    if (isLiked)
                        Icons.Filled.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,

                contentDescription = "Secure Photo",

                tint =
                    if (isLiked)
                        Color.Red
                    else
                        Color.Gray
            )
        }

    }


}
// SecurePicCheckbox ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//CHECKBOXES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// GRID ITEM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun GridItem(photo: Photo, photoViewModel: PhotoViewModel, firebaseViewModel: FirebaseViewModel) {

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            PicCheckbox(photo = photo, photoViewModel = photoViewModel )
            SecurePicCheckbox(photo = photo, photoViewModel = photoViewModel , firebaseViewModel = firebaseViewModel)
        }


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
fun BoxScope.BoxExtras ( photoViewModel: PhotoViewModel, gameViewModel: GameViewModel, challengeType: ChallengeType){

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

            // award coins and refresh if user confirmed the deletion
            val deletedCount = photoViewModel.uiState.deletionList.size
            gameViewModel.onDeletion(deletedCount)
            gameViewModel.updateChallengeProgress(
                challengeType,
                deletedCount
            )

            photoViewModel.clearDeletionList()
            photoViewModel.saveDeletedCountToFirebase()
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
        verticalAlignment = Alignment.Top
    ) {
        // Avatar Section
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFB2A8FF), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                val characterResId = state2.characterSprite.toIntOrNull() ?: R.drawable.character_base_single_green
                JumpingCharacter(
                    baseId = characterResId,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            val userLevel = (state2.grownPlants * 3) + state2.plantLevel + 1
            Text(text = "Lvl. $userLevel", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Stats Section
        Column(modifier = Modifier.weight(1f)) {
            // Username (Optional, but keeping it if needed)
            // Text(text = state2.userName, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            // Dynamic Experience Section
            val currentLevelThreshold = if (state2.plantLevel > 0) gameViewModel.plantThresholds[state2.plantLevel - 1] else 0
            val nextLevelThreshold = if (state2.plantLevel < gameViewModel.plantThresholds.size) gameViewModel.plantThresholds[state2.plantLevel] else gameViewModel.plantThresholds.last()
            
            val progressInCurrentLevel = state2.completedChallenges - currentLevelThreshold
            val neededForNextLevel = nextLevelThreshold - currentLevelThreshold

            StatBar(
                label = "Experience", 
                current = progressInCurrentLevel, 
                max = neededForNextLevel, 
                color = Color(0xFFC8E6C9) // Matching the green theme in screenshot
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), 
                horizontalArrangement = Arrangement.SpaceBetween, 
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🔥", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${state2.streak} Week Streak", 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold, 
                        color = Color(0xFFF44336)
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🪙", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${state2.coins}", 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }
            }
        }
    }
}
// HEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//                    .background(Color(0xFFB2A8FF), RoundedCornerShape(8.dp))
//                    .padding(4.dp)
//            ) {
//                val characterResId = state2.characterSprite.toIntOrNull() ?: R.drawable.character_base_single_green
//                JumpingCharacter(
//                    baseId = characterResId,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }

            val userLevel = (state2.grownPlants * 3) + state2.plantLevel + 1
            Text(text = "Lvl. $userLevel", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }


        Spacer(modifier = Modifier.width(16.dp))




        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🔥", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${state2.streak}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF44336))
            }
            Text(text = "🪙 ${state2.coins}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }


    }
}
// GRIDHEADER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


// ECOPOPUP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Composable
fun EcoPopUp(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel){
    val unlockedFact = gameViewModel.uiState.newlyUnlockedFact

    if (unlockedFact != null) {

        AlertDialog(

            onDismissRequest = {
                gameViewModel.clearUnlockedFactPopup()
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        gameViewModel.clearUnlockedFactPopup()
                    }
                ) {
                    Text("Nice!")
                }
            },

            title = {
                Text("🌱 New Eco Fact Unlocked!")
            },

            text = {

                Column {

                    Text(
                        text = unlockedFact.title
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = unlockedFact.fact
                    )
                }
            }
        )
    }
}
// ECOPOPUP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



//Box(modifier = Modifier.fillMaxSize()) {
//    Text("hi")
//    //verify user password panel
//    //verify user password panel
//    if (userVerification){
//        AlertDialog(
//            onDismissRequest = { },
//
//            title = {
//                Text("Password Verification")
//            },
//
//            text = {
//                Column {
//
//                    Text("Enter your account password")
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = {
//                            password = it
//                        },
//                        label = {
//                            Text("Password")
//                        }
//                    )
//                }
//            },
//
//            confirmButton = {
//                TextButton(onClick = {
//
//                    viewModel.verifyPassword(
//                        password = password,
//                        onSuccess = {
//
//                            userVerification = false
//
//
//
//                            //from here
//                        },
//
//                        onError = {
//                            Toast.makeText(context, "Verification failed", Toast.LENGTH_SHORT).show()
//                        }
//                    )
//
//                }) {
//                    Text("Verify")
//                }
//            },
//
//            dismissButton = {
//                TextButton(onClick = {
//                    userVerification = false
//                }) {
//                    Text("Cancel")
//                }
//            }
//
//        )
//    }
//
//} // to here