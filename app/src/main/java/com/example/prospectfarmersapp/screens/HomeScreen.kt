package com.example.prospectfarmersapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class FarmerStats(
    val totalFarmers: Int = 120,
    val newFarmersThisMonth: Int = 25,
    val activeFarmers: Int = 98
)

data class Farmer(
    val name: String,
    val location: String,
    val dateAdded: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val stats = FarmerStats()
    val recentFarmers = listOf(
        Farmer("John Doe", "North Region", "2024-12-20"),
        Farmer("Jane Smith", "South Region", "2024-12-19"),
        Farmer("Bob Wilson", "East Region", "2024-12-18"),
        Farmer("Alice Brown", "West Region", "2024-12-17"),
        Farmer("Tom Davis", "Central Region", "2024-12-16")
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_farmer_screen") }
            ) {
                Icon(Icons.Filled.Add, "Add Farmer")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Stats Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Farmers",
                    value = stats.totalFarmers.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "New This Month",
                    value = stats.newFarmersThisMonth.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Active Farmers",
                    value = stats.activeFarmers.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            // Recent Farmers Section
            Text(
                text = "Recently Added Farmers",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recentFarmers) { farmer ->
                    FarmerCard(farmer = farmer)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerCard(farmer: Farmer) {
    Card(
        onClick = { /* Navigate to farmer details */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = farmer.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = farmer.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = farmer.dateAdded,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}