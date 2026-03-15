package com.example.organivy.ui.pages

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.organivy.ui.theme.AppTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Surface
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.R
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.organivy.ui.theme.textLight


@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Toast.makeText(context, "FAB Clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text(
                    text = "Additional",
                    color = textLight,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) { }

            /*Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to OrganIvy Home!",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(text = "Hello")
            } */

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "  Welcome to OrganIvy User!",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            /*Surface(
                modifier = Modifier.padding(10.dp),
                border = BorderStroke(2.dp, Color.Black),
                contentColor = Color.White,
                shape = RoundedCornerShape(10.dp),
                color = Color.DarkGray
            ) {
                Text(text= "hi how are you", fontSize = 20.sp)
            } */

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 60.dp, 30.dp, 450.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Username", Modifier.padding(15.dp))
                Text(text = "Level", Modifier.padding(15.dp))
                Text(text = "Progress Bar", Modifier.padding(15.dp))
            }

            Card(
                modifier = Modifier
                    .padding(20.dp, 400.dp, 210.dp, 30.dp)
                    .size(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Badges Earning", Modifier.padding(20.dp))
            }

            Card(
                modifier = Modifier
                    .padding(210.dp, 400.dp, 20.dp, 30.dp)
                    .size(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Facts Learned", Modifier.padding(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(onNavigateToProfile = {})
}
