package com.example.prospectfarmersapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prospectfarmersapp.components.StatCard
import com.example.prospectfarmersapp.components.FarmerCard
import com.example.prospectfarmersapp.models.FarmersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: FarmersViewModel = viewModel()) {
    val farmerStats = viewModel.farmerStats.collectAsState().value
    val recentFarmers = viewModel.recentFarmers.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }

    // Custom colors
    val customGreen = Color(0xFF1B5E20)
    val lightGreen = Color(0xFFE8F5E9)

    //am still get 0 results even when api has resutls
    val totalFarmers = farmerStats?.totalFarmers?.toString()
    val totalProspects = farmerStats?.totalProspects?.toString() ?: "0"
    val totalUsers = farmerStats?.totalUsers?.toString() ?: "0"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "My Dashboard",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = customGreen
                            )
                        )
                        Text(
                            "Welcome back",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Implement settings */ }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = customGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightGreen
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("add_farmer_screen") },
                containerColor = customGreen,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, "Add Farmer")
                Spacer(Modifier.width(8.dp))
                Text("Add Farmer")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { },
                    active = false,
                    onActiveChange = { },
                    placeholder = { Text("Search farmers...") },
                    leadingIcon = { Icon(Icons.Default.Search, "Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // Search suggestions can go here
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = lightGreen
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatCard(
                            title = "Total Users",
                            value = totalUsers.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Active Farms",
                            value = totalFarmers.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Prospects",
                            value = totalProspects.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recently Added Farmers",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = customGreen
                        )
                    )
                    TextButton(
                        onClick = { /* View all farmers */ },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = customGreen
                        )
                    ) {
                        Text("View All")
                    }
                }
            }

            items(recentFarmers) { farmer ->
                FarmerCard(
                    farmer = farmer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}