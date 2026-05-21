package com.example.organivy.ui.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.Header
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.organivy.R
import com.example.organivy.sign_in.OrganIvyViewModel


@Composable
fun CleaningPage (onNavigateToProfile: () -> Unit,
                  onNavigateToCamera: () -> Unit,
                  onNavigateToDownloads: () -> Unit,
                  onNavigateToScreenshots: () -> Unit,
                  onNavigateToWhatsappImages: () -> Unit,
                  onNavigateToOld: () -> Unit,
                  onNavigateToLarge: () -> Unit,
                  onNavigateToBlurry: () -> Unit,
                  onNavigateToDuplicated: () -> Unit,
                  photoViewModel: PhotoViewModel?,
                  gameViewModel: GameViewModel,
                  //hasPermission: Boolean,
                  onNavigateToSecureFolder: () -> Unit,
                  onNavigateToSecureFolderLock: () -> Unit
) {

    //val state by photoViewModel!!.uiState.collectAsState()
    val state = photoViewModel?.uiState// ?: PhotoState()
    val state2 = gameViewModel.uiState






    Box(modifier = Modifier.fillMaxSize()) {

        //code in here
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp,),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                val photoViewModel = viewModel<PhotoViewModel>()
                Header(photoViewModel = photoViewModel, gameViewModel = gameViewModel)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Text(
                    text = "Make the World Cleaner One Photo at a Time!",
                    //fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }


            item{
                Box{
                    Image(
                        painter = painterResource(id = R.drawable.long_card),
                        contentDescription = "Shelf Image",
                        modifier = Modifier//.height(140.dp).width(100.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop,


                    )
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.Center)){
                        Text(text = "Top category:", Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                                color = MaterialTheme.colorScheme.onPrimary)
                        Text(text = "Photos deleted this week:", Modifier.padding(horizontal = 20.dp),
                            color = MaterialTheme.colorScheme.onPrimary)
                    }

                }

            }

            item { Spacer(modifier = Modifier.height(5.dp)) }


            //ADDDDDDDDDDDDDDDDDDED
            item { Spacer(modifier = Modifier.height(30.dp)) }

            item {
                Text(
                    text = "Categories:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            // row 1
            item{
                Shelves(
                    name = "Camera" ,
                    list = "no. of photos: ${state?.cameraPicsList?.size}",
                    navigate = onNavigateToCamera,
                    name2 = "Screenshots",
                    list2 = "no. of photos: ${state?.screenshotsList?.size}",
                    navigate2 = onNavigateToScreenshots,
                ) }
            // row 2
            item{
                Shelves(
                    name = "Downloads" ,
                    list = "no. of photos: ${state?.downloadsList?.size}",
                    navigate = onNavigateToDownloads,
                    name2 = "Whatsapp",
                    list2 = "no. of photos: ${state?.whatsappPicsList?.size}",
                    navigate2 = onNavigateToWhatsappImages,
                ) }


            item { Spacer(modifier = Modifier.height(35.dp)) }

            item {
                Text(
                    text = "Types:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            //row1
            item{
            Shelves(
                name = "Old Images" ,
                list = "no. of photos: ${state?.oldPhotos}",
                navigate = onNavigateToOld,
                name2 = "Large Images",
                list2 = "no. of photos: ${state?.largePhotos}",
                navigate2 = onNavigateToLarge,
            ) }
            //row2
            item{
                Shelves(
                    name = "Blurry Images" ,
                    list = "no. of photos: ${state?.blurryPhotos}",
                    navigate = onNavigateToBlurry,
                    name2 = "Duplicates",
                    list2 = "no. of photos: ${state?.duplicatePhotos}",
                    navigate2 = onNavigateToDuplicated,
                ) }
            //addeds endish



            android.util.Log.d("PHOTO_TEST", "Large photos: ${state?.largePhotos}")
            android.util.Log.d("PHOTO_TEST", "Old photos: ${state?.oldPhotos}")
            android.util.Log.d("PHOTO_TEST", "Duplicate groups: ${state?.duplicatePhotos}")

            item { Spacer(modifier = Modifier.height(60.dp)) }

        }


        Box(modifier = Modifier.clickable { onNavigateToSecureFolderLock() }.align(alignment = Alignment.BottomEnd)) {


            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = "Game card",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit,
                //filterQuality = FilterQuality.None

            )

            Text(
                text = "\uD83D\uDD12 ",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
        }





        if (state?.isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(150.dp, 200.dp)
            )

        }

    }





    // end on cleaning page
}
@Composable
fun Shelves (name:String, list: String, navigate: () -> Unit, name2:String, list2: String, navigate2: () -> Unit){
    Row{

        Box( modifier = Modifier.width(160.dp)){
            TextButton(onClick = navigate) {
                Text(name, Modifier.padding(15.dp),
                    style = MaterialTheme.typography. headlineSmall,
                    fontSize = 15.sp)
            }
            Image(
                painter = painterResource(id = R.drawable.shelf),
                contentDescription = "Shelf Image",
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Fit,
                //filterQuality = FilterQuality.None

            )
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = list,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Box( modifier = Modifier.width(180.dp)){
            TextButton(onClick = navigate2) {
                Text(name2, Modifier.padding(15.dp,15.dp,5.dp,15.dp),
                    style = MaterialTheme.typography. headlineSmall,
                    fontSize = 15.sp)
            }
            Image(
                painter = painterResource(id = R.drawable.shelf),
                contentDescription = "Shelf Image",
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Fit,
                //filterQuality = FilterQuality.None

            )
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = list2,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}


@Composable
fun SecureFolderLockScreen(
    onCancel: () -> Unit,
    viewModel: OrganIvyViewModel = viewModel(),
    onNavigateToSecureFolder: () -> Unit
) {

    var password by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,

        title = { Text("Secure Folder") },

        text = {
            Column {
                Text("Enter password to continue")

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }
                )
            }
        },

        confirmButton = {
            TextButton(onClick = {

                viewModel.verifyPassword(
                    password = password,
                    onSuccess = {
                        onNavigateToSecureFolder()
                    },
                    onError = {
                        // show toast

                    }
                )
            }) {
                Text("Unlock")
            }
        },

        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

// Preview function goes outside MainActivity class
//@Preview(showBackground = true)
//@Composable
//fun PreviewCleaningPage() {
//    CleaningPage(onNavigateToProfile = {}, onNavigateToCamera = {} , onNavigateToDownloads = {}, onNavigateToScreenshots = {},
//        onNavigateToWhatsappImages = {}, onNavigateToOld = {}, onNavigateToLarge = {}, onNavigateToDuplicated = {},
//        onNavigateToBlurry = {},  photoViewModel = null, gameViewModel = null
//    )
//}