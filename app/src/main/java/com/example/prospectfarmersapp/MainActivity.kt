package com.example.prospectfarmersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.prospectfarmersapp.api.RetrofitInstance
import com.example.prospectfarmersapp.ui.screens.*
import com.example.prospectfarmersapp.ui.components.BottomNavBar
import com.example.prospectfarmersapp.ui.components.TopNavBar
import com.example.prospectfarmersapp.ui.theme.ProspectFarmersAppTheme
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

        // Initialize Retrofit API calls after the activity is created
        fetchFarmerStats()
        fetchRecentFarmers()
    }

    private fun fetchFarmerStats() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.apiService.getFarmStats()
                if (response.isSuccessful) {
                    val farmStats = response.body()
                    // Handle the successful response, e.g., update UI
                    println("Farm Stats: $farmStats")
                } else {
                    // Handle failure, e.g., show error message
                    println("Error fetching farm stats: ${response.errorBody()}")
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions
                println("HTTP Error: ${e.message}")
            } catch (e: Throwable) {
                // Handle other errors (e.g., no internet connection)
                println("Error: ${e.message}")
            }
        }
    }

    private fun fetchRecentFarmers() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.apiService.getRecentFarmers()
                if (response.isSuccessful) {
                    val recentFarmers = response.body()?.farmers
                    // Handle the successful response, e.g., update UI
                    println("Recent Farmers: $recentFarmers")
                } else {
                    // Handle failure, e.g., show error message
                    println("Error fetching recent farmers: ${response.errorBody()}")
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions
                println("HTTP Error: ${e.message}")
            } catch (e: Throwable) {
                // Handle other errors (e.g., no internet connection)
                println("Error: ${e.message}")
            }
        }
    }
}
