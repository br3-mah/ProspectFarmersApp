package com.example.prospectfarmersapp.ui.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import java.util.UUID

@Composable
fun AddFarmerScreen(navController: NavHostController) {
    var farmerName by remember { mutableStateOf("") }
    var farmerLocation by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var temporalId = UUID.randomUUID().toString()  // Generate unique ID for the farmer
    var phoneNumberError by remember { mutableStateOf("") }

    // Validate phone number
    fun isValidPhoneNumber(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Farmer", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Farmer Name Input
        OutlinedTextField(
            value = farmerName,
            onValueChange = { farmerName = it },
            label = { Text("Farmer Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Input with validation
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                phoneNumberError = if (isValidPhoneNumber(it)) "" else "Invalid phone number"
            },
            label = { Text("Phone Number") },
            isError = phoneNumberError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (phoneNumberError.isNotEmpty()) {
            Text(text = phoneNumberError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Farmer Location Input
        OutlinedTextField(
            value = farmerLocation,
            onValueChange = { farmerLocation = it },
            label = { Text("Farmer Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Save Farmer Button
        Button(
            onClick = {
                if (farmerName.isNotEmpty() && farmerLocation.isNotEmpty() && isValidPhoneNumber(phoneNumber)) {
                    // Save Farmer Logic
                    // Example: Save the farmer's data to a database, API, or local storage
                    navController.popBackStack()
                } else {
                    // Display error message if any field is invalid
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Farmer")
        }
    }
}
