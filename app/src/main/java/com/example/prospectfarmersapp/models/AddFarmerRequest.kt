package com.example.prospectfarmersapp.models

data class AddFarmerRequest(
    val fName: String,
    val lName: String,
    val phoneNumber: String,
    val email: String,
    val farmerLocation: String,
    val farmName: String,
    val farmAddress: String,
    val farmSize: String,
    val typeOfFarming: String,
    val mobileMoneyNumber: String,
    val bankAccountNumber: String,
    val bankName: String,
    val isProspect: Boolean
)
