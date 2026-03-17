package com.example.organivy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.organivy.ui.components.BottomNavigationBar
import com.example.organivy.ui.pages.GreenJournalScreen
import com.example.organivy.ui.pages.HomeScreen
import com.example.organivy.ui.pages.ShopScreen
import com.example.organivy.ui.pages.CleaningPage
import com.example.organivy.ui.pages.GardenScreen
import com.example.organivy.ui.pages.StatsandImpactPage
import com.example.organivy.ui.pages.SettingsScreen
import com.example.organivy.ui.subpages.cleaning.CameraSubscreen
import com.example.organivy.ui.subpages.cleaning.DownloadsSubscreen
import com.example.organivy.ui.subpages.cleaning.ScreenshotsSubscreen
import com.example.organivy.ui.subpages.cleaning.WhatsappImagesSubscreen
import com.example.organivy.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Setting dynamicColor to false ensures your custom backgroundLight color is used
            AppTheme(dynamicColor = false) {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) },
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {


                            composable("home") {
                                HomeScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("garden") {
                                GardenScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("shop") {
                                ShopScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }

//                            composable("cleaning") {
//                                CleaningPage(onNavigateToProfile = { navController.navigate("profile") })
//                            }
                            navigation(
                                startDestination = "cleaning_main",
                                route = "cleaning"
                            ) {

                                composable("cleaning_main") {
                                    CleaningPage(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        onNavigateToCamera = { navController.navigate("camera") },
                                        onNavigateToDownloads = { navController.navigate("downloads") },
                                        onNavigateToScreenshots = { navController.navigate("screenshots") },
                                        onNavigateToWhatsappImages = { navController.navigate("whatsapp_images") },
                                    )
                                }

                                composable("camera") {
                                    CameraSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }

                                composable("downloads") {
                                    DownloadsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }

                                composable("whatsapp_images") {
                                    WhatsappImagesSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }

                                composable("screenshots") {
                                    ScreenshotsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }
                            }

                            composable("stats") {
                                StatsandImpactPage(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("settings") {
                                SettingsScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("journal") {
                                GreenJournalScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }
                        }
                    }
                }
            }
        }
    }
}
