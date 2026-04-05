package com.example.organivy.ui.subpages.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.organivy.ui.pages.header


@Composable
fun LoginScreen(onNavigateToProfile: () -> Unit,
                onNavigateToSignUp: () -> Unit,
                onNavigateToForget: () -> Unit,
                onNavigateToHome: () -> Unit

){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp, ),
        //verticalArrangement = Arrangement.Top,
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item{Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Login",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center

        )}

        item { Spacer(modifier = Modifier.height(60.dp)) }

        item{Text(
            text = "Username",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,

            )}

        item{Text(
            text = "Email",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,

            )}


        item{Text(
            text = "Password",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )}

        item {
            TextButton(onClick = onNavigateToForget ) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 16.sp,)
            }
        }

        item { Spacer(modifier = Modifier.height(50.dp)) }
        
        item {
            Button(
                onClick = onNavigateToHome
//                        navController.navigate("app") {
//                    popUpTo("auth") { inclusive = true }
//                }
                // idk above this^
                ,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    "Login", Modifier.padding(20.dp),
                    fontSize = (20.sp)
                )
            }
        }
        item { Spacer(modifier = Modifier.height(40.dp)) }

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

        item{

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(130.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Don't have an Account?",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                TextButton(onClick = onNavigateToSignUp ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp,)
                }
            }

        }









    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onNavigateToProfile = {}, onNavigateToSignUp = {}, onNavigateToForget = {}, onNavigateToHome = {})
}