package com.example.organivy.ui.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.Header
import com.example.organivy.viewmodel.GameViewModel
import com.example.organivy.viewmodel.PhotoViewModel
import android.widget.Toast
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organivy.data.Header
import com.example.organivy.sign_in.OrganIvyViewModel
import com.example.organivy.viewmodel.ThemeViewModel
import kotlin.getValue


@Composable
fun SettingsScreen(onNavigateToProfile: () -> Unit,
                   selectedTheme: ThemeOption,
                   onThemeChange: (ThemeOption) -> Unit,
                   onLogout: () -> Unit
) {

    val themeViewModel: ThemeViewModel = viewModel()
    val selectedTheme by themeViewModel::selectedTheme
    val authViewModel: OrganIvyViewModel = viewModel()
    val photoViewModel: PhotoViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()
    val context = LocalContext.current

    val isDark = when (selectedTheme) {
        ThemeOption.SYSTEM -> isSystemInDarkTheme()
        ThemeOption.LIGHT -> false
        ThemeOption.DARK -> true
    }

    val cardContainerColor = if (isDark) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }

    val cardContentColor = if (isDark) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {


        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Text(
                text = "Settings Page",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        //these will all have text buttons later or maybe a drop down. idk yet
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text(text = "Accounts")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            authViewModel.signOut { error ->
                                if (error != null) {
                                    Toast.makeText(
                                        context,
                                        "Logout failed: $error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    photoViewModel.clearUserSession()
                                    gameViewModel.clearUserSession()
                                    Toast.makeText(
                                        context,
                                        "Logged out",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onLogout()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Log out")
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = cardContentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Appearance",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                //will be on page

                RadioButtons(
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeChange
                )


            }
        }
/*
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor, 
                    contentColor = cardContentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Sound Preferences", Modifier.padding(15.dp))
                //will be on page

            }
        }

 */

        item {
            Card(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://arianne-zmd.github.io/ay25-26-cmp208-OrganIvy/privacy.html")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = cardContentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(
                    text = "Privacy Policy",
                    modifier = Modifier.padding(15.dp)
                )
            }
        }

        item {
            Card(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://arianne-zmd.github.io/ay25-26-cmp208-OrganIvy/terms.html")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = cardContentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Terms & Conditions", Modifier.padding(15.dp))
                //webpage

            }
        }

        item {
            Card(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://arianne-zmd.github.io/ay25-26-cmp208-OrganIvy/help.html")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardContainerColor,
                    contentColor = cardContentColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            ) {
                Text(text = "Help", Modifier.padding(15.dp))
                //might make this go to a help webpage. idk yet tho
            }
        }

//        item {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp, 20.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = cardContainerColor,
//                    contentColor = cardContentColor
//                ),
//                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
//            ) {
//                Text(text = "Invite People!", Modifier.padding(15.dp))
//
//            }
//        }
    }



}


// for display modes
enum class ThemeOption {
    SYSTEM, LIGHT, DARK
}

@Composable
fun RadioButtons(selectedTheme: ThemeOption,
                 onThemeSelected: (ThemeOption) -> Unit

) {

    val options = listOf(
        ThemeOption.SYSTEM,
        ThemeOption.LIGHT,
        ThemeOption.DARK
    )

    Row {
        options.forEach { option ->

            val text = when(option) {
                ThemeOption.SYSTEM -> "System"
                ThemeOption.LIGHT -> "Light Mode"
                ThemeOption.DARK -> "Dark Mode"
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onThemeSelected(option) }
                    .padding(8.dp)
            ) {

                RadioButton(
                    selected = (selectedTheme == option),
                    onClick = { onThemeSelected(option) }
                )
                Text(text = text, modifier = Modifier.padding(start = 8.dp))

            }
        }
    }

}
// for display modes


// Preview function goes outside MainActivity class
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewSettingScreen() {
//    SettingsScreen(onNavigateToProfile = {}, selectedTheme = AppThemeOption.SYSTEM, onThemeChange = {})
//}