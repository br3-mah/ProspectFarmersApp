package com.example.prospectfarmersapp.models

import com.google.gson.annotations.SerializedName

data class FarmerStats(
    @SerializedName("total_prospects") val totalProspects: Int,
    @SerializedName("total_farmers") val totalFarmers: Int,
    @SerializedName("total_users") val totalUsers: Int
)
