package com.example.organivy.ui.pages

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.common.math.LinearTransformation.horizontal


@Composable
fun CleaningPage(onNavigateToProfile: () -> Unit) {
    /*Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cleaning Page")
    } */

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

        item { Spacer(modifier = Modifier.height(110.dp)) }

        item {
            Text(
                text = "Types:",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


    }








    val types = listOf("Camera", "Screenshots", "Downloads", "WhatsApp Images")
    LazyRow (
        modifier = Modifier
            .padding(20.dp, 410.dp, 20.dp, 30.dp)
            .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        items(types) { type ->

             Card(
                 modifier = Modifier,
                 colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),){
                Text(type, Modifier.padding(15.dp))
            }
            //item ends here
        }
    }

    val storageTypes = listOf("Old Images", "Large Images", "Blurry Images", "Duplicated Images")
    LazyRow (
        modifier = Modifier
            .padding(20.dp, 550.dp, 20.dp, 30.dp)
            .height(100.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        items(storageTypes) { type ->

            Card(
                modifier = Modifier,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ), elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),){
                Text(type, Modifier.padding(15.dp))
            }
            //item ends here
        }
    }


/*
    Card(
        //modifier = Modifier
        //    .padding(5.dp)
        //    .wrapContentHeight(),
        modifier = Modifier
            //.padding(200.dp,350.dp)
            .padding(210.dp, 400.dp, 20.dp, 30.dp)
            .size(200.dp),


        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),



        ){
        Text(text = "Facts Learned", Modifier.padding(20.dp))

    } */


    // end on cleaning page
}

// Preview function goes outside MainActivity class
@Preview(showBackground = true)
@Composable
fun PreviewCleaningPage() {
    CleaningPage(onNavigateToProfile = {})
}