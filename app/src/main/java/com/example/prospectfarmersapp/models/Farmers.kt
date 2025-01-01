package com.example.prospectfarmersapp.models
import com.google.gson.annotations.SerializedName

data class Farmers(
    @SerializedName("fname") val fName: String,
    @SerializedName("lname") val lName: String,
    @SerializedName("farm_name") val farmName: String,
    @SerializedName("farm_size") val farmSize: String,
    @SerializedName("farm_address") val farmAddress: String,
    @SerializedName("created_at") val createdAt: String
)
