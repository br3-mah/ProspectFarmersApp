package com.example.prospectfarmersapp.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search

sealed class BottomNavItem(val route: String, val icon: @Composable () -> Unit, val label: String) {
    object Home : BottomNavItem("home_screen", { Icon(Icons.Filled.Home, contentDescription = "Home") }, "Home")
    object AddFarmer : BottomNavItem("add_farmer_screen", { Icon(Icons.Filled.Add, contentDescription = "Add") }, "Add")
    object Search : BottomNavItem("search_farmer_screen", { Icon(Icons.Filled.Search, contentDescription = "Search") }, "Search")
}

val bottomNavItems = listOf(BottomNavItem.Home, BottomNavItem.AddFarmer, BottomNavItem.Search)

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
