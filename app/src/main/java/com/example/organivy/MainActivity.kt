package com.example.organivy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.organivy.data.BlurDetection
import com.example.organivy.data.PhotoScanner
import com.example.organivy.ui.components.BottomNavigationBar
import com.example.organivy.ui.pages.GreenJournalScreen
import com.example.organivy.ui.pages.HomeScreen
import com.example.organivy.ui.pages.ShopScreen
import com.example.organivy.ui.pages.CleaningPage
import com.example.organivy.ui.pages.GardenScreen
import com.example.organivy.ui.pages.SettingsScreen
import com.example.organivy.ui.pages.BadgePage
import com.example.organivy.ui.pages.StatsandImpactPage
import com.example.organivy.ui.pages.ThemeOption
import com.example.organivy.ui.subpages.cleaning.BlurryPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.CameraSubscreen
import com.example.organivy.ui.subpages.cleaning.DownloadsSubscreen
import com.example.organivy.ui.subpages.cleaning.DuplicatedPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.LargePicsSubscreen
import com.example.organivy.ui.subpages.cleaning.OldPicsSubscreen
import com.example.organivy.ui.subpages.cleaning.ScreenshotsSubscreen
import com.example.organivy.ui.subpages.cleaning.WhatsappImagesSubscreen
import com.example.organivy.ui.subpages.garden.WorldMapSubscreen
import com.example.organivy.ui.theme.AppTheme
import com.example.organivy.viewmodel.PhotoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var hasPermissionState by mutableStateOf(false)

        val requestPermissionLauncher =
            registerForActivityResult(
                androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {

                    hasPermissionState = isGranted

                    lifecycleScope.launch(Dispatchers.IO) {
//                        val scanner = PhotoScanner(this@MainActivity)
//                        val photo = scanner.logScanImages()
//
//                        //val blurDetector = BlurDetection(this@MainActivity)

//                        // Filtering the photos
//                        val largePics = withContext(Dispatchers.Default) {
//                            photo.filter { pic -> pic.size >= 500_000 }
//                        }
//                        val oldPics = withContext(Dispatchers.Default) {
//                            photo.filter { pic -> pic.dateAdded <= (System.currentTimeMillis() / 1000) - 31_556_952L }
//                        }
//                        val duplicatedPics = withContext(Dispatchers.Default) {
//                            photo.groupBy { pic -> pic.size }.filter { pic -> pic.value.size > 1 }
//                        }
////                        val blurryPics = withContext(Dispatchers.Default) {
//                            photo.filter { blurDetector.isImageBlurry(it) }
//                        }

                        withContext(Dispatchers.Main) {
                            // Logging results
//                            android.util.Log.d("PHOTO_TEST", "Total photos: ${photo.size}")
//                            android.util.Log.d("PHOTO_TEST", "Large photos: ${largePics.size}")
//                            android.util.Log.d("PHOTO_TEST", "Old photos: ${oldPics.size}")
//                            android.util.Log.d("PHOTO_TEST", "Duplicate groups: ${duplicatedPics.size}")
                            //android.util.Log.d("PHOTO_TEST", "Blurry photos: ${blurryPics.size}")


                        }
                    }
                } else {
                    // permission denied
                    Toast.makeText(this, "Permission required to access photos", Toast.LENGTH_SHORT).show()
                }
            }

        // Request permissions
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        setContent {


            val hasPermission = hasPermissionState
            val viewModel: PhotoViewModel = viewModel()
            LaunchedEffect(hasPermission) {
                if (hasPermission) {
                    viewModel.loadPhotos()
                }
            }

            var selectedTheme by remember { mutableStateOf(ThemeOption.SYSTEM) }

            val isDark = when (selectedTheme) {
                ThemeOption.SYSTEM -> isSystemInDarkTheme()
                ThemeOption.LIGHT -> false
                ThemeOption.DARK -> true
            }

            // Setting dynamicColor to false ensures your custom backgroundLight color is used

            AppTheme(darkTheme = isDark, dynamicColor = false ) {

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
                                HomeScreen(
                                    onNavigateToProfile = { navController.navigate("profile") },
                                    onNavigateToBadges = { navController.navigate("badges") },
                                    onNavigateToStatsandImpact = { navController.navigate("stats") },
                                    onNavigateToShop = { navController.navigate("shop") },
                                    onNavigateToJournal = { navController.navigate("journal") }
                                )
                            }
                            composable("garden") {
                                GardenScreen(
                                    onNavigateToProfile = { navController.navigate("profile") },
                                    onNavigateToShop = { navController.navigate("shop") },
                                    onNavigateToJournal = { navController.navigate("journal") },
                                    onNavigateToStats = { navController.navigate("stats") },
                                    onNavigateToMap = { navController.navigate("map") }
                                )
                            }
                            composable("shop") {
                                ShopScreen(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("badges") {
                                BadgePage(onNavigateToProfile = { navController.navigate("profile") })
                            }
                            composable("stats") {
                                StatsandImpactPage(onNavigateToProfile = {
                                    navController.navigate(
                                        "profile"
                                    )
                                })
                            }
                            composable("map") {
                                WorldMapSubscreen(onNavigateToProfile = {
                                    navController.navigate(
                                        "profile"
                                    )
                                })
                            }

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

                                        onNavigateToOld = { navController.navigate("old") },
                                        onNavigateToLarge = { navController.navigate("large") },
                                        onNavigateToBlurry = { navController.navigate("blurry") },
                                        onNavigateToDuplicated = { navController.navigate("duplicated") },
                                        viewModel = viewModel
                                    )
                                }

                                composable("camera") {
                                    CameraSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }

                                composable("downloads") {
                                    DownloadsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }

                                composable("whatsapp_images") {
                                    WhatsappImagesSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }

                                composable("screenshots") {
                                    ScreenshotsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }


                                composable("old") {
                                    OldPicsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }

                                composable("large") {
                                    LargePicsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") },
                                        viewModel = viewModel
                                    )
                                }


                                composable("blurry") {
                                    BlurryPicsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }

                                composable("duplicated") {
                                    DuplicatedPicsSubscreen(
                                        onNavigateToProfile = { navController.navigate("profile") }
                                    )
                                }


                            }

                            composable("settings") {
                                SettingsScreen(
                                    onNavigateToProfile = { navController.navigate("profile") },
                                    selectedTheme = selectedTheme,
                                    onThemeChange = { ThemeOption -> selectedTheme = ThemeOption })
                            }
                            composable("journal") {
                                GreenJournalScreen(onNavigateToProfile = {
                                    navController.navigate(
                                        "profile"
                                    )
                                })
                            }

                            // Temporary profile route to prevent crash
                            composable("profile") {
                                SettingsScreen(
                                    onNavigateToProfile = { navController.navigate("profile") },
                                    selectedTheme = selectedTheme,
                                    onThemeChange = { ThemeOption -> selectedTheme = ThemeOption })
                            }
                        }
                    }
                }

        }

        }
    }
}
