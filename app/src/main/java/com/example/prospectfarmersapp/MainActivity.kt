package com.example.prospectfarmersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState // Correct the import here
import com.example.prospectfarmersapp.ui.screens.*
import com.example.prospectfarmersapp.ui.components.BottomNavBar
import com.example.prospectfarmersapp.ui.components.TopNavBar
import com.example.prospectfarmersapp.ui.theme.ProspectFarmersAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProspectFarmersAppTheme {
                val navController = rememberNavController()

                // Wrap Scaffold to conditionally show BottomNavBar
                Scaffold(
                    topBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        if (currentRoute != "login_screen") {
                            TopNavBar(title = currentRoute?.capitalize() ?: "Prospect Farmers")
                        }
                    },
                    bottomBar = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        if (currentRoute != "login_screen") {
                            BottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash_screen") { SplashScreen(navController) }
                        composable("login_screen") { LoginScreen(navController) }
                        composable("home_screen") { HomeScreen(navController) }
                        composable("add_farmer_screen") { AddFarmerScreen(navController) }
                        composable("search_farmer_screen") { SearchFarmerScreen(navController) }
                    }
                }
            }
        }
    }
}
