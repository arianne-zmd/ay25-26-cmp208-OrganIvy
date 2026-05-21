package com.example.organivy.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Garden,
        BottomNavItem.Cleaning,
        BottomNavItem.Settings
    )

    NavigationBar {
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry.value?.destination
        val currentRoute = currentDestination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        painter = painterResource(id = item.icon), 
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    ) 
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Dynamically determine the root of the current navigation flow.
                        // If we are in the 'app' graph, we pop to it to maintain internal state.
                        val popRoute = if (currentDestination?.hierarchy?.any { it.route == "app" } == true) {
                            "app"
                        } else {
                            navController.graph.startDestinationRoute ?: "auth"
                        }

                        popUpTo(popRoute) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
