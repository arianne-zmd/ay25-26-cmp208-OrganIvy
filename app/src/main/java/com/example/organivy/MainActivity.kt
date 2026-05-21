package com.example.organivy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
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
import com.example.organivy.ui.pages.OnboardingScreen
import com.example.organivy.ui.subpages.authentication.ForgetPasswordScreen
import com.example.organivy.ui.subpages.authentication.LoginScreen
import com.example.organivy.ui.subpages.authentication.SignUpScreen
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.organivy.ui.pages.SecureFolderLockScreen
import com.example.organivy.ui.pages.SecureFolderPage
import com.example.organivy.ui.subpages.onboarding.OnStartOnboarding1
import com.example.organivy.ui.subpages.onboarding.OnStartOnboarding2
import com.example.organivy.ui.subpages.onboarding.OnStartOnboarding3
import com.example.organivy.ui.subpages.onboarding.OnStartOnboarding4
import com.example.organivy.ui.subpages.onboarding.OnStartOnboarding5
import com.example.organivy.ui.subpages.garden.SeedsFallingAnimation
import com.example.organivy.ui.subpages.garden.PlantBuddingAnimation
import com.example.organivy.ui.subpages.garden.DaisyBloomingAnimation
import com.example.organivy.ui.subpages.garden.FinalShotAnimation
import com.example.organivy.viewmodel.FirebaseViewModel
import com.example.organivy.viewmodel.GameViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //var hasPermissionState by mutableStateOf(false)

        setContent {




            // ViewModels scoped to this Activity — shared across all NavHost destinations
            val photoViewModel: PhotoViewModel = viewModel()
            val gameViewModel: GameViewModel = viewModel()       // game + Users/{uid} sync
            val firebaseViewModel: FirebaseViewModel = viewModel() // secure folder Firestore
//



            var selectedTheme by remember { mutableStateOf(ThemeOption.SYSTEM) }

            val isDark = when (selectedTheme) {
                ThemeOption.SYSTEM -> isSystemInDarkTheme()
                ThemeOption.LIGHT -> false
                ThemeOption.DARK -> true
            }

            // Setting dynamicColor to false ensures your custom backgroundLight color is used

            AppTheme(darkTheme = isDark, dynamicColor = false ) {



                val navController = rememberNavController()

                MainApp(navController,
                    photoViewModel,
                    gameViewModel,
                    selectedTheme,
                    { newTheme -> selectedTheme = newTheme },
                    //hasPermission = hasPermission,
                    firebaseViewModel = firebaseViewModel
                    )








                // enter above here ^
            }

        }
    }


}


// adding this


//FOR SET CONTENT
@Composable

fun MainApp(navController: NavHostController,
            photoViewModel: PhotoViewModel,
            gameViewModel: GameViewModel,
            selectedTheme: ThemeOption,
            onThemeChange: (ThemeOption) -> Unit,
            //hasPermission: Boolean,
            firebaseViewModel: FirebaseViewModel
) {
    //val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Hide the bottom bar on auth screens
    val showBottomBar = currentRoute !in listOf(
        "loading",
        "onboarding1",
        "onboarding2",
        "onboarding3",
        "onboarding4",
        "onboarding5",
        "login",
        "signup",
        "forget_password",
        "onboarding",
        "seed_transition"
    )



//    val showBottomBar = navBackStackEntry?.destination?.hierarchy
//        ?.any { it.route in listOf(
//            "home",
//            "garden",
//            "cleaning_main",
//            "journal",
//            "settings"
//        ) } == true

    var homeReady by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        //bottomBar = { BottomNavigationBar(navController) },

        bottomBar = { if (showBottomBar && homeReady) BottomNavigationBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val gameState = gameViewModel.uiState
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .blur(if (gameState.pendingGrowthAnimation != null) 16.dp else 0.dp),
                color = MaterialTheme.colorScheme.background
            ) {

                // Calculate startDestination only ONCE when MainApp is first created.
                // This prevents the NavHost from resetting to "app" immediately after a successful signup/login.
                val initialStartDestination = remember {
                    if (FirebaseAuth.getInstance().currentUser != null) "app" else "auth"
                }

                NavHost(
                    navController = navController,
                    startDestination = initialStartDestination
                ) {

                    authGraph(navController, gameViewModel, photoViewModel)
                    appGraph(navController,
                        photoViewModel,
                        gameViewModel,
                        selectedTheme,
                        onThemeChange,
                        //hasPermission,
                        firebaseViewModel,
                        onHomeReady = { homeReady = true })

                }

            }

            // Global Growth Animation Overlay
            if (gameState.pendingGrowthAnimation != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f))
                        .clickable(enabled = false) {}, // Consume clicks
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Level Up!",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        when (gameState.pendingGrowthAnimation) {
                            1 -> PlantBuddingAnimation(onAnimationFinished = { gameViewModel.clearGrowthAnimation() })
                            2 -> DaisyBloomingAnimation(onAnimationFinished = { gameViewModel.clearGrowthAnimation() })
                            3 -> FinalShotAnimation(onAnimationFinished = { gameViewModel.clearGrowthAnimation() })
                            else -> gameViewModel.clearGrowthAnimation()
                        }
                    }
                }
            }
        }
    }
}



fun NavGraphBuilder.authGraph(navController: NavHostController, gameViewModel: GameViewModel,photoViewModel: PhotoViewModel,) {
    navigation(
        startDestination = "onboarding1",
        route = "auth"
    ) {

        composable("onboarding1") {

            OnStartOnboarding1(onNavigateToNext = { navController.navigate("onboarding2") })
        }

        composable("onboarding2") {

            OnStartOnboarding2(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNext = { navController.navigate("onboarding3") }
            )
        }

        composable("onboarding3") {

            OnStartOnboarding3(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNext = { navController.navigate("onboarding4") }
            )
        }

        composable("onboarding4") {

            OnStartOnboarding4(

                onNavigateBack = { navController.popBackStack() },
                onNavigateToNext = { navController.navigate("onboarding5") }

            )
        }

        composable("onboarding5") {

            OnStartOnboarding5(

                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = { navController.navigate("login")},
                photoViewModel= photoViewModel,

            )
        }

        composable("login") {
            LoginScreen( onNavigateToProfile = { navController.navigate("profile")},
                onNavigateToSignUp = { navController.navigate("signup")},
                onNavigateToForget = { navController.navigate("forget_password")},
                // After Firebase Auth succeeds, play seeds falling animation
                onNavigateToHome = { navController.navigate("seed_transition") }
                )
        }

        composable("signup") {
            SignUpScreen( onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToForget = { navController.navigate("forget_password")},
                onNavigateToLogin = { navController.navigate("login")},
                onNavigateToHome = { navController.navigate("onboarding") }, // Navigate to avatar creation first
                )
        }

        composable("forget_password") {
            ForgetPasswordScreen( onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToLogin = { navController.navigate("login")}
                )
        }

        composable("onboarding") {
            OnboardingScreen(
                gameViewModel = gameViewModel,
                onFinishOnboarding = {
                    gameViewModel.saveUserDataToFirebase()
                    navController.navigate("seed_transition") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("seed_transition") {
            val gameState = gameViewModel.uiState
            
            if (gameState.isInitialLoadComplete) {
                if (gameState.plantLevel > 0) {
                    // Skip animation if already past level 0
                    LaunchedEffect(Unit) {
                        navController.navigate("app") {
                            popUpTo("seed_transition") { inclusive = true }
                        }
                    }
                } else {
                    // Show animation for level 0 users
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        SeedsFallingAnimation(onAnimationFinished = {
                            navController.navigate("app") {
                                popUpTo("seed_transition") { inclusive = true }
                            }
                        })
                    }
                }
            } else {
                // Loading user progress to decide whether to show animation
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

//main app

fun NavGraphBuilder.appGraph(navController: NavHostController,
                             photoViewModel: PhotoViewModel,
                             gameViewModel: GameViewModel,
                             selectedTheme: ThemeOption,
                             onThemeChange: (ThemeOption) -> Unit,
                             //hasPermission: Boolean,
                             firebaseViewModel: FirebaseViewModel,
                             onHomeReady: () -> Unit

) {



    navigation(
        startDestination = "home",
        route = "app"
    ) {



        composable("home") {
            HomeScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToBadges = { navController.navigate("badges") },
                onNavigateToStatsandImpact = { navController.navigate("stats") },
                onNavigateToShop = { navController.navigate("shop") },
                onNavigateToJournal = { navController.navigate("journal") },
                onNavigateToGarden = { navController.navigate("garden") },
                photoViewModel = photoViewModel,
                gameViewModel = gameViewModel,
                onReady = onHomeReady
            )
        }
        composable("garden") {
            GardenScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToShop = { navController.navigate("shop") },
                onNavigateToJournal = { navController.navigate("journal") },
                onNavigateToStats = { navController.navigate("stats") },
                onNavigateToMap = { navController.navigate("map") },
                gameViewModel = gameViewModel,
            )
        }
        composable("shop") {
            ShopScreen(
                gameViewModel = gameViewModel,
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("badges") {
            BadgePage(onNavigateToProfile = { navController.navigate("profile") },
                photoViewModel = photoViewModel,
                gameViewModel = gameViewModel,

            )
        }
        composable("stats") {
            StatsandImpactPage(onNavigateToProfile = { navController.navigate("profile") },
                photoViewModel = photoViewModel,
                gameViewModel = gameViewModel,
            )
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
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    //hasPermission = hasPermission,
                    onNavigateToSecureFolder = { navController.navigate("secure") },
                    onNavigateToSecureFolderLock = { navController.navigate("securelock") }
                )
            }

            composable("camera") {
                CameraSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("downloads") {
                DownloadsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("whatsapp_images") {
                WhatsappImagesSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("screenshots") {
                ScreenshotsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }


            composable("old") {
                OldPicsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("large") {
                LargePicsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }


            composable("blurry") {
                BlurryPicsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile")},
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("duplicated") {
                DuplicatedPicsSubscreen(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("secure") {
                SecureFolderPage(
                    onNavigateToProfile = { navController.navigate("profile") },
                    photoViewModel = photoViewModel,
                    gameViewModel = gameViewModel,
                    firebaseViewModel = firebaseViewModel
                )
            }

            composable("securelock") {
                SecureFolderLockScreen(
                    onCancel = {
                        navController.popBackStack()
                    },
                    onNavigateToSecureFolder = { navController.navigate("secure") }
                )
            }


        }

        composable("settings") {
            SettingsScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                selectedTheme = selectedTheme,
                onThemeChange = { newTheme -> onThemeChange(newTheme) },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("app") { inclusive = true }
                    }
                })
        }
        composable("journal") {
            GreenJournalScreen(onNavigateToProfile = { navController.navigate("profile") },
                gameViewModel = gameViewModel,)
        }

        // Temporary profile route to prevent crash
        composable("profile") {
            SettingsScreen(
                onNavigateToProfile = { navController.navigate("profile") },
                selectedTheme = selectedTheme,
                onThemeChange = { newTheme -> onThemeChange(newTheme) },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("app") { inclusive = true }
                    }
                })
        }

    }
}

