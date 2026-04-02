package com.example.organivy.ui.pages

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue

@Composable
fun SettingsScreen(onNavigateToProfile: () -> Unit,
                   selectedTheme: ThemeOption,
                   onThemeChange: (ThemeOption) -> Unit
) {



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {

        item { header() }
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
                Text(text = "Accounts", Modifier.padding(15.dp))
                // under this will be add, delete, change accounts

            }
        }

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
                //WARNINGGGGGGGGGGGG
                //button does not work. idk y. if you can fix it then fix it. i added
                // something in the themes file at the end for this  so i can take it out
                //and there's some stuff in mainkt as well

            }
        }

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
                Text(text = "Sound Preferences", Modifier.padding(15.dp))
                //will be on page

            }
        }

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
                Text(text = "Privacy Policy", Modifier.padding(15.dp))
                //webpage

            }
        }

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
                Text(text = "Terms & Conditions", Modifier.padding(15.dp))
                //webpage

            }
        }

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
                Text(text = "Help", Modifier.padding(15.dp))
                //might make this go to a help webpage. idk yet tho
            }
        }

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
                Text(text = "Invite People!", Modifier.padding(15.dp))

            }
        }
    }



}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

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


// Preview function goes outside MainActivity class
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewSettingScreen() {
//    SettingsScreen(onNavigateToProfile = {}, selectedTheme = AppThemeOption.SYSTEM, onThemeChange = {})
//}