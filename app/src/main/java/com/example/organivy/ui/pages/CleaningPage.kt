package com.example.organivy.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.MainActivity
import com.example.organivy.data.PhotoScanner
import com.google.common.math.LinearTransformation.horizontal


@Composable
fun CleaningPage(onNavigateToProfile: () -> Unit,
                 onNavigateToCamera: () -> Unit,
                 onNavigateToDownloads: () -> Unit,
                 onNavigateToScreenshots: () -> Unit,
                 onNavigateToWhatsappImages: () -> Unit) {



//    val large by remember { mutableStateOf(MainActivity.PhotoStats.largePhotos) }
//    val old by remember { mutableStateOf(MainActivity.PhotoStats.oldPhotos) }
//    val duplicates by remember { mutableStateOf(MainActivity.PhotoStats.duplicateGroups) }
//    val blurry by remember { mutableStateOf(MainActivity.PhotoStats.blurryPhotos) }

    //code in here
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp, ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        //verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item { Spacer(modifier = Modifier.height(16.dp))  }

        item {
            Text(
                text = "Make the World Cleaner One Photo \n at a Time!",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp))  }

        item{
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

                ){
                Text(text = "Top category:", Modifier.padding(15.dp))
                Text(text =  "Photos deleted this week:", Modifier.padding(15.dp))

            }
        }

        item { Spacer(modifier = Modifier.height(35.dp))  }

        item{

            Button(onClick = {
                // boo
            },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Delete and Grow", Modifier.padding(20.dp),
                    fontSize= (16.sp) )
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
            "WhatsApp Images" to onNavigateToWhatsappImages)
        val temp2 = listOf("bat", "ball", "cap", "glove", "shoe")
        item {
            LazyRow(
                modifier = Modifier
                    .padding(10.dp, 20.dp,)
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)

            ) {
                itemsIndexed(catergories ) { index, (name,action) ->

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
                            text = "no. of photos: "+ temp2[index],
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


        val storageTypes = listOf("Old Images", "Large Images", "Blurry Images", "Duplicated Images")
        val temp = listOf("${MainActivity.PhotoStats.oldPhotos}", "${MainActivity.PhotoStats.largePhotos}", "${MainActivity.PhotoStats.blurryPhotos}", "${MainActivity.PhotoStats.duplicateGroups}")

//        android.util.Log.d("PHOTO_TEST", "Large photos: ${MainActivity.PhotoStats.largePhotos}")
//        android.util.Log.d("PHOTO_TEST", "Old photos: ${MainActivity.PhotoStats.oldPhotos}")
//        android.util.Log.d("PHOTO_TEST", "Duplicate groups: ${MainActivity.PhotoStats.duplicateGroups}")
//        android.util.Log.d("PHOTO_TEST", "Blurry photos: ${MainActivity.PhotoStats.blurryPhotos}")
        item {
            LazyRow(
                modifier = Modifier
                    .padding(10.dp, 20.dp)
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)

            ) {

                itemsIndexed(storageTypes, ) { index, name ->

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
                            Text(name, Modifier.padding(15.dp))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "no. of photos: "+ temp[index],
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
    CleaningPage(onNavigateToProfile = {}, onNavigateToCamera = {} , onNavigateToDownloads = {}, onNavigateToScreenshots = {}, onNavigateToWhatsappImages = {})
}