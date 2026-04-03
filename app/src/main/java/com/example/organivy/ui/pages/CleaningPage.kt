package com.example.organivy.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.organivy.data.PhotoState
import com.example.organivy.viewmodel.PhotoViewModel


@Composable
fun CleaningPage(onNavigateToProfile: () -> Unit,
                 onNavigateToCamera: () -> Unit,
                 onNavigateToDownloads: () -> Unit,
                 onNavigateToScreenshots: () -> Unit,
                 onNavigateToWhatsappImages: () -> Unit,
                 onNavigateToOld: () -> Unit,
                 onNavigateToLarge: () -> Unit,
                 onNavigateToBlurry: () -> Unit,
                 onNavigateToDuplicated: () -> Unit,
                 viewModel: PhotoViewModel?
) {

    val state = viewModel?.uiState ?: PhotoState()



    if (state?.isLoading == true)  {
        CircularProgressIndicator()
        return
    }



        //code in here
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(20.dp,),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            //verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                header()
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Text(
                    text = "Make the World Cleaner One Photo at a Time!",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Card(
                    //modifier = Modifier
                    //    .padding(5.dp)
                    //    .wrapContentHeight(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(110.dp),


                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 5.dp
                    ),

                    ) {
                    Text(text = "Top category:", Modifier.padding(15.dp))
                    Text(text = "Photos deleted this week:", Modifier.padding(15.dp))

                }
            }

            item { Spacer(modifier = Modifier.height(35.dp)) }

            item {

                Button(
                    onClick = {
                        // boo
                        viewModel?.loadPhotos()

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Delete and Grow", Modifier.padding(20.dp),
                        fontSize = (16.sp)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(60.dp)) }

            item {
                Text(
                    text = "Categories:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            val catergories = listOf(
                "Camera   " to onNavigateToCamera,
                "Screenshots" to onNavigateToScreenshots,
                "Downloads" to onNavigateToDownloads,
                "WhatsApp Images" to onNavigateToWhatsappImages
            )
            val temp2 = listOf(
                "${state?.cameraPicsList?.size}",
                "${state?.screenshotsList?.size}",
                "${state?.downloadsList?.size}",
                "${state?.whatsappPicsList?.size}"
            )
            item {
                LazyRow(
                    modifier = Modifier
                        .padding(10.dp, 20.dp,)
                        .height(130.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)

                ) {
                    itemsIndexed(catergories) { index, (name, action) ->

                        Column(
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .height(120.dp)

                        ) {

                            Card(
                                modifier = Modifier,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 5.dp
                                ),
                            ) {

                                TextButton(onClick = action) {
                                    Text(name, Modifier.padding(15.dp))
                                }

                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "no. of photos: " + temp2[index],
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }


                    } // <-- end of types
                }
            }

            item {
                Text(
                    text = "Types:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }


            val storageTypes = listOf(
                "Old Images" to onNavigateToOld,
                "Large Images" to onNavigateToLarge,
                "Blurry Images" to onNavigateToBlurry,
                "Duplicated Images" to onNavigateToDuplicated
            )
            val temp = listOf(
                "${state?.oldPhotos}",
                "${state?.largePhotos}",
                "blurry",
                "${state?.duplicatePhotos}"
            )

            android.util.Log.d("PHOTO_TEST", "Large photos: ${state?.largePhotos}")
            android.util.Log.d("PHOTO_TEST", "Old photos: ${state?.oldPhotos}")
            android.util.Log.d("PHOTO_TEST", "Duplicate groups: ${state?.duplicatePhotos}")
//        android.util.Log.d("PHOTO_TEST", "Blurry photos: ${MainActivity.PhotoStats.blurryPhotos}")
            item {
                LazyRow(
                    modifier = Modifier
                        .padding(10.dp, 20.dp)
                        .height(130.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)

                ) {

                    itemsIndexed(storageTypes) { index, (name, action) ->

                        Column(
                            modifier = Modifier
                                .padding(0.dp, 0.dp)
                                .height(120.dp)

                        ) {

                            Card(
                                modifier = Modifier,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 5.dp
                                ),
                            ) {
                                TextButton(onClick = action) {
                                    Text(name, Modifier.padding(15.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "no. of photos: " + temp[index],
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }


                    } // <-- end of types


                }

            }


        }














    // end on cleaning page
}

// Preview function goes outside MainActivity class
@Preview(showBackground = true)
@Composable
fun PreviewCleaningPage() {
    CleaningPage(onNavigateToProfile = {}, onNavigateToCamera = {} , onNavigateToDownloads = {}, onNavigateToScreenshots = {},
        onNavigateToWhatsappImages = {}, onNavigateToOld = {}, onNavigateToLarge = {}, onNavigateToDuplicated = {},
        onNavigateToBlurry = {},  viewModel = null
    )
}