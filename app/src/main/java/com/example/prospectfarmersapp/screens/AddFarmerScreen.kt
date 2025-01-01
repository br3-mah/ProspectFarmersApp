package com.example.prospectfarmersapp.ui.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel // Add this import
import com.example.prospectfarmersapp.models.AddFarmerRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


@Composable
fun AddFarmerScreen(navController: NavHostController) {
    val viewModel: AddFarmerViewModel = viewModel()

    var currentStep by remember { mutableStateOf(1) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Step 1 Fields
    var fName by remember { mutableStateOf("") } // First name
    var lName by remember { mutableStateOf("") } // Last name
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var isProspect by remember { mutableStateOf(false) } // New checkbox field

    // Step 2 Fields
    var farmerLocation by remember { mutableStateOf("") }
    var farmName by remember { mutableStateOf("") }
    var farmAddress by remember { mutableStateOf("") }

    // Step 3 Fields
    var farmSize by remember { mutableStateOf("") }
    var typeOfFarming by remember { mutableStateOf("") }
    var mobileMoneyNumber by remember { mutableStateOf("") }
    var bankAccountNumber by remember { mutableStateOf("") }
    var bankName by remember { mutableStateOf("") }

    // Validate functions
    fun isValidPhoneNumber(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(Color(0xFFE8F5E9))
            .padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register New Farmer", style = MaterialTheme.typography.headlineLarge, color = Color(0xFF388E3C))
        Spacer(modifier = Modifier.height(16.dp))

        // Step 1: Personal Information
        AnimatedVisibility(visible = currentStep == 1) {
            Column {
                OutlinedTextField(
                    value = fName,
                    onValueChange = { fName = it },
                    label = { Text("Farmer First Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = lName,
                    onValueChange = { lName = it },
                    label = { Text("Farmer Last Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        phoneNumberError = if (isValidPhoneNumber(it)) "" else "Invalid phone number"
                    },
                    label = { Text("Phone Number") },
                    isError = phoneNumberError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                if (phoneNumberError.isNotEmpty()) {
                    Text(text = phoneNumberError, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = if (isValidEmail(it)) "" else "Invalid email address"
                    },
                    label = { Text("Email") },
                    isError = emailError.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )
                if (emailError.isNotEmpty()) {
                    Text(text = emailError, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Checkbox for isProspect
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isProspect,
                        onCheckedChange = { isProspect = it }
                    )
                    Text(text = "Is Prospect", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (fName.isNotEmpty() && lName.isNotEmpty() && isValidPhoneNumber(phoneNumber) && isValidEmail(email)) {
                            currentStep++
                        } else {
                            // Display error message if any field is invalid
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text="Next")
                }
            }
        }

        // Step 2: Farm Information
        AnimatedVisibility(visible = currentStep == 2) {
            Column {
                OutlinedTextField(
                    value = farmerLocation,
                    onValueChange = { farmerLocation = it },
                    label = { Text("Farmer Address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=farmName,
                    onValueChange={farmName=it},
                    label={Text("Farm Name")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=farmAddress,
                    onValueChange={farmAddress=it},
                    label={Text("Farm Address")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(16.dp))

                Button(
                    onClick={
                        if (farmerLocation.isNotEmpty() && farmName.isNotEmpty() && farmAddress.isNotEmpty()) {
                            currentStep++
                        } else {
                            // Display error message if any field is invalid
                        }
                    },
                    modifier=Modifier.fillMaxWidth()
                ) {
                    Text(text="Next")
                }
            }
        }

        // Step 3: Financial Information
        AnimatedVisibility(visible=currentStep==3) {
            Column {
                OutlinedTextField(
                    value=farmSize,
                    onValueChange={farmSize=it},
                    label={Text("Farm Size")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=typeOfFarming,
                    onValueChange={typeOfFarming=it},
                    label={Text("Type of Farming")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=mobileMoneyNumber,
                    onValueChange={mobileMoneyNumber=it},
                    label={Text("Mobile Money Number")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=bankAccountNumber,
                    onValueChange={bankAccountNumber=it},
                    label={Text("Bank Account Number")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(8.dp))

                OutlinedTextField(
                    value=bankName,
                    onValueChange={bankName=it},
                    label={Text("Bank Name")},
                    modifier=Modifier.fillMaxWidth(),
                    shape=RoundedCornerShape(10.dp)
                )
                Spacer(modifier=Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (farmSize.isNotEmpty() && typeOfFarming.isNotEmpty() &&
                            mobileMoneyNumber.isNotEmpty() && bankAccountNumber.isNotEmpty() && bankName.isNotEmpty()
                        ) {
                            isLoading = true
                            errorMessage = ""

                            // Create farmer data object with updated fields
                            val farmerRequest = AddFarmerRequest(
                                fName = fName,
                                lName = lName,
                                phoneNumber = phoneNumber,
                                email = email,
                                farmerLocation = farmerLocation,
                                farmName = farmName,
                                farmAddress = farmAddress,
                                farmSize = farmSize,
                                typeOfFarming = typeOfFarming,
                                mobileMoneyNumber = mobileMoneyNumber,
                                bankAccountNumber = bankAccountNumber,
                                bankName = bankName,
                                isProspect = isProspect
                            )
                            viewModel.submitFarmer(context, farmerRequest) {
                                navController.popBackStack() // Navigate back on success
                            }
                        } else {
                            errorMessage = "Please fill all fields correctly."
                        }
                    },
                    modifier=Modifier.fillMaxWidth()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text(text = "Submit")
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

