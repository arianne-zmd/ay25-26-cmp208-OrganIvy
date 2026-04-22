package com.example.organivy.ui.subpages.authentication

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun ForgetPasswordScreen (onNavigateToProfile: () -> Unit,
                         onNavigateToLogin: () -> Unit
){

    Box(modifier = Modifier.fillMaxSize()
        .padding(20.dp, ),

    ) {
//        Surface(
//            color = Color.Red, modifier = Modifier
//                .size(150.dp)
//                .align(alignment = Alignment.TopEnd)
//        ) {}


        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(20.dp,),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Reset Password",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Text(
                    text = "Enter your Email",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            item {
                Button(
                    onClick = onNavigateToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Reset Password", Modifier.padding(20.dp),
                        fontSize = (16.sp)
                    )
                }
            }


            item {

            }


        }

//        Surface(
//            color = Color.Yellow, modifier = Modifier
//                .size(150.dp)
//                .align(alignment = Alignment.TopCenter)
//        ) {}
    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForgetPasswordScreen() {
    ForgetPasswordScreen(onNavigateToProfile = {}, onNavigateToLogin = {})
}