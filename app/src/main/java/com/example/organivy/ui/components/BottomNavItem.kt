package com.example.organivy.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Garden : BottomNavItem("garden", Icons.Default.Build, "Garden")
    object Shop : BottomNavItem("shop", Icons.Default.ShoppingCart, "Shop")
    object Cleaning : BottomNavItem("cleaning", Icons.Default.CheckCircle, "Cleaning")
    object Stats : BottomNavItem("stats", Icons.Default.Info, "Stats")
    object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
    object Journal : BottomNavItem("journal", Icons.Default.Build, "Journal")
}