package com.example.organivy.ui.pages


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.data.BoxExtras
import com.example.organivy.data.GridItem
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.GridHeader
import com.example.organivy.data.SafeDeletion
import com.example.organivy.sign_in.OrganIvyViewModel
import com.example.organivy.viewmodel.FirebaseViewModel


@Composable
fun SecureFolderPage(onNavigateToProfile: () -> Unit,
                    photoViewModel: PhotoViewModel?,
                    gameViewModel: GameViewModel,
                     firebaseViewModel: FirebaseViewModel
){

    LaunchedEffect(Unit) {
        photoViewModel?.loadSecurePhotos(firebaseViewModel)
    }

    val state = photoViewModel?.uiState



    if (state != null && state.secureFolderList.isNotEmpty()) {
        LazyGridScreenSecureFP(photoViewModel = photoViewModel,  gameViewModel = gameViewModel, firebaseViewModel = firebaseViewModel)
    } else {
        Text(
            text = "No images in Secure Folder",
            color = MaterialTheme.colorScheme.onBackground
        )
    }


}

@Composable
fun LazyGridScreenSecureFP(photoViewModel: PhotoViewModel, gameViewModel: GameViewModel, viewModel: OrganIvyViewModel = viewModel(), firebaseViewModel: FirebaseViewModel ) {
    val state = photoViewModel.uiState  //: PhotoState()

    var deletionVerification by remember { mutableStateOf(false) }
    var userVerification by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }



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


                GridItem(photo = photo, photoViewModel = photoViewModel, firebaseViewModel = firebaseViewModel)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
        // under grid


        // CUSTOM BOX EXTRASSSSSSSSSSSSSSSS

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

                // ONLY Award coins and refresh if user confirmed the deletion
                val deletedCount = photoViewModel.uiState.deletionList.size
                gameViewModel.onDeletion(deletedCount)

                photoViewModel.removeDeletedPhotosFromSecureFolder()
                photoViewModel.clearDeletionList()

                photoViewModel.loadSecurePhotos(firebaseViewModel)

                Toast.makeText(context, "Photos deleted successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        val safeDeletion = remember { SafeDeletion(context) }


        Button(
            onClick = {
                deletionVerification = true


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

        //verify deletion panel
        if (deletionVerification){
            AlertDialog(
                onDismissRequest = { },

                title = {
                    Text("Delete Photo")
                },

                text = {
                    Text("Are you sure you want to delete this photo?")
                },

                confirmButton = {
                    TextButton(onClick = {
                        deletionVerification = false
                        userVerification = true

                    }) {
                        Text("Delete")
                    }
                },

                dismissButton = {
                    TextButton(onClick = {
                        deletionVerification = false

                    }) {
                        Text("Cancel")
                    }
                }
            )
        }


        //verify user password panel
        if (userVerification){
            AlertDialog(
                onDismissRequest = { },

                title = {
                    Text("Password Verification")
                },

                text = {
                    Column {

                        Text("Enter your account password")

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            label = {
                                Text("Password")
                            }
                        )
                    }
                },

                confirmButton = {
                    TextButton(onClick = {

                        viewModel.verifyPassword(
                            password = password,
                            onSuccess = {

                                userVerification = false

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

                            onError = {
                                Toast.makeText(context, "Verification failed", Toast.LENGTH_SHORT).show()
                            }
                        )

                    }) {
                        Text("Verify")
                    }
                },

                dismissButton = {
                    TextButton(onClick = {
                        userVerification = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }


        // CUSTOM BOX EXTRASSSSSSSSSSSSSSSS


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



    } // put under here


}

