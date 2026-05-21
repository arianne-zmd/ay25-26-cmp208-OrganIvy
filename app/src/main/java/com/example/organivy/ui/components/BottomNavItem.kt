package com.example.organivy.ui.components

import com.example.organivy.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object Garden : BottomNavItem("garden", R.drawable.garden, "Garden")
    object Shop : BottomNavItem("shop", R.drawable.shop, "Shop")
    object Cleaning : BottomNavItem("cleaning_main", R.drawable.cleaning_nav, "Cleaning")
    object Stats : BottomNavItem("stats", R.drawable.stats, "Stats")
    object Settings : BottomNavItem("settings", R.drawable.settings, "Settings")
    object Journal : BottomNavItem("journal", R.drawable.journal, "Journal")
}
