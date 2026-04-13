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
fun SignUpScreen(onNavigateToProfile: () -> Unit,
                 onNavigateToForget: () -> Unit,
                 onNavigateToLogin: () -> Unit,
                 onNavigateToHome: () -> Unit
){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp, ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item { Spacer(modifier = Modifier.height(50.dp)) }

        item{Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Up",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )}

        item { Spacer(modifier = Modifier.height(40.dp)) }

        item{Text(
            text = "Enter Email",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}

        item{Text(
            text = "Username",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}
        item{Text(
            text = "Username may only contain alphanumeric characters and underscores. Spaces are not allowed.",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}

        item{Text(
            text = "Password",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}
        item{Text(
            text = "Password should be 7-15 characters and include a number and a lowercase letter",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}



        item{Text(
            modifier = Modifier.fillMaxWidth(),
            text = "-------------------- or --------------------",
            fontSize =15.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center

        )}

        item{Text(
            text = "Continue with Google",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}

        item { Spacer(modifier = Modifier.height(40.dp)) }

        item {

            Button(
                onClick = onNavigateToHome,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Create Account", Modifier.padding(20.dp),
                    fontSize = (16.sp)
                )
            }
        }
    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(onNavigateToProfile = {}, onNavigateToForget = {}, onNavigateToLogin = {}, onNavigateToHome = {})
}