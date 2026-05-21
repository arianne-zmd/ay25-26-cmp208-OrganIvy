plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    // Setting up firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.organivy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.organivy"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    // Added for WaterDrop and WbSunny icons
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.google.fonts)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.unit)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // ── Firebase dependencies ─────────────────────────────────────────────────
    // BOM (Bill of Materials): pins compatible versions for all firebase-* libraries.
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)          // optional usage/events in console
    implementation("com.google.firebase:firebase-auth")       // email login, session uid
    implementation("com.google.firebase:firebase-firestore")  // Users, devices, securePhotos
    implementation("com.google.firebase:firebase-storage")    // reserved for future image upload
    // Google Sign-In helper (auth_backup/); enable when adding Google button to login
    implementation("com.google.android.gms:play-services-auth:21.5.1")
    // Bridges Firebase Task.await() used in OrganIvyViewModel signIn/signUp/reset
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")
    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")


}
