package com.example.organivy.ui.pages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.sp
import com.example.organivy.R
import com.example.organivy.data.BadgeItem
import com.example.organivy.data.BadgeType






// i want to add recycler view here, bedges are grayed out here you dont have them
// remove padding from between card titles!!!!!!!!!!!!!!!
@Composable
fun StatsandImpactPage(onNavigateToProfile: () -> Unit,
                       photoViewModel: PhotoViewModel,
                       gameViewModel: GameViewModel,
) {


    val state = photoViewModel.uiState
    val state2 = gameViewModel.uiState


    var displayAccountStats by remember {
        mutableStateOf(false)
    }








    // local stats ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    if (displayAccountStats) {
        AccountStats(
            onBack = {
                displayAccountStats = false
            }
        )
    }else {




        Row {
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
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
                    text = " Local Stats ",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }




            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .size(200.dp)
                    .fillMaxWidth(0.5f)
                    .padding(1.dp, 30.dp, 20.dp, 0.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    displayAccountStats = true
                }


            ) {
                Text(
                    text = " Account Stats ",
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }






        Box{
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .fillMaxSize()
                    .padding(20.dp)
                    .padding(top = 60.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )


            ){
                LazyColumn{
                    item {
                        StatBox("Deleted Images", "${state.totalDeletedPics}")
                    }


                    item {
                        StatBox("Deleted Images' \nBytes", "${state.totalDeletedBytes}")
                    }


                    item {
                        StatBox("CO2 saved \nEstimation", "${state.totalCO2Saved}")
                    }


                    item {
                        StatBox("Plant Level", "${state2.plantLevel}")
                    }


                    item {
                        StatBox("Streak", "0")
                    }


                    item {
                        StatBox("Challenges Completed", "0")
                    }


                    item {
                        StatBox("Badges Earned", "0")
                    }
                }
            }


        }
    }


}






@Composable
fun AccountStats(onBack: () -> Unit) {


    Row{
        Card(modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth(0.5f)
            .size(200.dp)
            .padding(20.dp, 30.dp, 1.dp, 0.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = onBack




        ) { Text(text = " Local Stats ",
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )}


        Card(modifier = Modifier
            .clip(RectangleShape)
            .size(200.dp)
            .fillMaxWidth(0.5f)
            .padding(1.dp, 30.dp, 20.dp, 0.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),




            ) { Text(text = " Account Stats ",
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )}
    }








    Box{


        Card(modifier = Modifier
            .clip(RectangleShape)
            .fillMaxSize()
            //.align(alignment = Alignment.Center)
            //.offset(x = 15.dp, y = 5.dp)
            .padding(20.dp)
            .padding(top = 60.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )


        ) {


            LazyColumn{
                item {
                    StatBox("Deleted Images", "0")
                }


                item {
                    StatBox("Deleted Images' Bytes", "0")
                }


                item {
                    StatBox("CO2 saved estimation", "0")
                }


                item {
                    StatBox("Coins", "0")
                }


                item {
                    StatBox("User Gardens", "0")
                }
            }


        }




    }


}


@Composable
fun StatBox(name: String, amount:String) {
    Card(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .padding(20.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )) {
        Row{
            Text(name, modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 18.sp)


            Text(amount,
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 18.sp
            )
        }


    }
}


/*
@Composable
fun StatsandImpactPage(onNavigateToProfile: () -> Unit,
                      photoViewModel: PhotoViewModel,
                      gameViewModel: GameViewModel,
                      ) {


   val state = photoViewModel.uiState
   val state2 = gameViewModel.uiState


   Column{
       Text("Total deleted images across devices ")
       Spacer(modifier = Modifier.height(20.dp))
       Text("Total estimated co2 saved across devices ")
       Spacer(modifier = Modifier.height(20.dp))
       Text("Total delete images : ${state.totalDeletedPics}")
       Spacer(modifier = Modifier.height(20.dp))
       Text("Total estimated co2 saved ")
   }
*/




/*
@Preview(showBackground = true)
//@Preview(device = Devices.PIXEL_7)
//@Preview(widthDp = 300, heightDp = 400)
@Composable
fun PreviewBadgePage() {
   StatsandImpactPage(
       onNavigateToProfile = {}
   )
}
*/

