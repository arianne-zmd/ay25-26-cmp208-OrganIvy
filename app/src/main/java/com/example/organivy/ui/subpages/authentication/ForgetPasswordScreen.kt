package com.example.organivy.ui.subpages.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ForgetPasswordScreen(onNavigateToProfile: () -> Unit,
                         onNavigateToLogin: () -> Unit
){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp, ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item { Spacer(modifier = Modifier.height(50.dp)) }

        item{Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Reset Password",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )}

        item{Text(
            text = "Enter your Email",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}

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




    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForgetPasswordScreen() {
    ForgetPasswordScreen(onNavigateToProfile = {}, onNavigateToLogin = {})
}