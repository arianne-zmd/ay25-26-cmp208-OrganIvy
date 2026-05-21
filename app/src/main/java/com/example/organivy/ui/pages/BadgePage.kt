package com.example.organivy.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.organivy.R
import com.example.organivy.data.BadgeItem
import com.example.organivy.data.BadgeType
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel


// i want to add recycler view here, bedges are grayed out here you dont have them
// remove padding from between card titles!!!!!!!!!!!!!!!
@Composable
fun BadgePage(
    onNavigateToProfile: () -> Unit,
    photoViewModel: PhotoViewModel,
    gameViewModel: GameViewModel,
              ) {

    val state = photoViewModel.uiState
    val state2 = gameViewModel.uiState

    var displayAllBadges by remember {
        mutableStateOf(false)
    }


    val allBadges = listOf(
        // Photo deletion badges
        BadgeItem(
            R.drawable.seed_starter,
            "Seed Starter",
            "Delete 50 photos",
            50,
            BadgeType.PHOTO_DELETE
        ),

        BadgeItem(
            R.drawable.sprout_keeper,
            "Sprout Keeper",
            "Delete 200 photos",
            200,
            BadgeType.PHOTO_DELETE
        ),

        BadgeItem(
            R.drawable.bloom_bringer,
            "Bloom Bringer",
            "Delete 500 photos",
            500,
            BadgeType.PHOTO_DELETE
        ),

        BadgeItem(
            R.drawable.forest_guardian,
            "Forest Guardian",
            "Delete 1,500 photos",
            1500,
            BadgeType.PHOTO_DELETE
        ),

        BadgeItem(
            R.drawable.digital_earth_saver,
            "Digital Earth Saver",
            "Delete 5,000+ photos",
            5000,
            BadgeType.PHOTO_DELETE
        ),

        // Storage cleanup badges
        BadgeItem(
            R.drawable.light_cleaner,
            "Light Cleaner",
            "Free 100MB",
            (1000000*100),
            BadgeType.BYTES_DELETE
        ),

        BadgeItem(
            R.drawable.space_saver,
            "Space Saver",
            "Free 1GB",
            (1000000*1000),
            BadgeType.BYTES_DELETE
        ),

        BadgeItem(
            R.drawable.storage_hero,
            "Storage Hero",
            "Free 5GB",
            (1000000*1000*5),
            BadgeType.BYTES_DELETE
        ),

        BadgeItem(
            R.drawable.device_liberator,
            "Device Liberator",
            "Free 10GB",
            (1000000*1000*10),
            BadgeType.BYTES_DELETE
        )


        )




    val earnedBadges = allBadges.filter { badge ->

        when (badge.badgeType) {

            BadgeType.PHOTO_DELETE -> {
                state.totalDeletedPics >= badge.amountToEarn
            }

            BadgeType.BYTES_DELETE -> {
                state.totalDeletedBytes >= badge.amountToEarn
            }
        }
    }



    //EARNED BADGES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    if (displayAllBadges) {
        AllBadges(
            onBack = {
                displayAllBadges = false
            },
            allBadges = allBadges,
            earnedBadges = earnedBadges
        )
    }else {


        Row {
            Card(
                modifier = Modifier.clip(RectangleShape)
                    .size(200.dp)
                    .fillMaxWidth(0.5f)
                    .padding(20.dp, 30.dp, 1.dp, 0.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            ) {
                Text(
                    text = " My Badges ",
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


            Card(
                modifier = Modifier.clip(RectangleShape)
                    .size(200.dp)
                    .fillMaxWidth(0.5f)
                    .padding(1.dp, 30.dp, 20.dp, 0.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    displayAllBadges = true
                }

            ) {
                Text(
                    text = " All Badges ",
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


        // DONT CHANGE ABOVEEEEEEEE

        Box{
            Card(
                modifier = Modifier.clip(RectangleShape)
                    .fillMaxSize()
                    .padding(20.dp).padding(vertical = 60.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )

            ){
                LazyColumn{
                        item {
                            Column {

                                earnedBadges.forEach { badge ->

                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Image(
                                            painter = painterResource(badge.imageRes),
                                            contentDescription = badge.name,
                                            modifier = Modifier.size(60.dp)
                                        )

                                        Column(
                                            modifier = Modifier.padding(start = 12.dp)
                                        ) {

                                            Text(text = badge.name)

                                            Text(text = badge.description)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }



@Composable
fun AllBadges(onBack: () -> Unit,
              allBadges: List<BadgeItem>,
              earnedBadges: List<BadgeItem>
) {

    Row{
        Card(modifier = Modifier.clip(RectangleShape)
            .fillMaxWidth(0.5f)
            . size(200.dp)
            .padding(20.dp, 30.dp, 1.dp, 0.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = onBack


        ) { Text(text = " My Badges ",
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )}

        Card(modifier = Modifier.clip(RectangleShape)
            . size(200.dp)
            .fillMaxWidth(0.5f)
            .padding(1.dp, 30.dp, 20.dp, 0.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),


        ) { Text(text = " All Badges ",
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )}
    }




    Box{

        Card(modifier = Modifier.clip(RectangleShape)
            .fillMaxSize()
            //.align(alignment = Alignment.Center)
            //.offset(x = 15.dp, y = 5.dp)
            .padding(20.dp) .padding(vertical = 60.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )

        ) {

            LazyColumn{
                item{
                    Column {

                        allBadges.forEach { badge ->

                            val isEarned = badge in earnedBadges

                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = painterResource(badge.imageRes),
                                    contentDescription = badge.name,

                                    modifier = Modifier
                                        .size(60.dp)
                                        .alpha(
                                            if (isEarned) 1f else 0.3f
                                        ),

                                    colorFilter =
                                        if (isEarned) null
                                        else ColorFilter.tint(Color.Gray)
                                )

                                Column(
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {

                                    Text(
                                        text = badge.name,

                                        color =
                                            if (isEarned)
                                                MaterialTheme.colorScheme.onBackground
                                            else
                                                Color.Gray
                                    )

                                    Text(
                                        text = badge.description,

                                        color =
                                            if (isEarned)
                                                MaterialTheme.colorScheme.onBackground
                                            else
                                                Color.Gray
                                    )
                                }

                                if (!isEarned) {

                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }


    }






}
/*
@Preview(showBackground = true)
//@Preview(device = Devices.PIXEL_7)
//@Preview(widthDp = 300, heightDp = 400)
@Composable
fun PreviewBadgePage() {
    BadgePage(
        onNavigateToProfile = {}, photoViewModel = null, gameViewModel = null
    )
}
*/