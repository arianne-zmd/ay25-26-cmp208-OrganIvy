package com.example.organivy.ui.subpages.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.sign_in.OrganIvyViewModel

/**
 * Password reset screen — no custom backend.
 *
 * WHY sendPasswordResetEmail (via OrganIvyViewModel):
 * Firebase sends the email and hosts the reset page. We only collect the address the
 * user registered with; invalid emails fail with Firebase's error message in a Toast.
 *
 * WHY navigate back to login on success:
 * User must set a new password from the email link, then sign in normally here.
 */
@Composable
fun ForgetPasswordScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: OrganIvyViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "Enter your email and we will send you a reset link.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }

            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                Button(
                    onClick = {
                        if (email.isBlank()) {
                            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        viewModel.sendPasswordReset(email) { error ->
                            if (error != null) {
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Password reset email sent. Check your inbox.",
                                    Toast.LENGTH_LONG
                                ).show()
                                onNavigateToLogin()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send reset link", modifier = Modifier.padding(16.dp), fontSize = 16.sp)
                }
            }

            item {
                TextButton(onClick = onNavigateToLogin) {
                    Text("Back to login")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForgetPasswordScreen() {
    ForgetPasswordScreen(onNavigateToProfile = {}, onNavigateToLogin = {})
}
