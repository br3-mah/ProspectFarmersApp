package com.example.prospectfarmersapp.models

import com.google.gson.annotations.SerializedName

data class RecentFarmersResponse(
    @SerializedName("farmers")
    val farmers: List<FarmersResponse>
)

data class FarmersResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("fname")
    val fName: String,

    @SerializedName("lname")
    val lName: String,

    @SerializedName("farm_name")
    val farmName: String,

    @SerializedName("farm_address")
    val location: String,

    @SerializedName("created_at")
    val joinedDate: String,

    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("fname")
    val fname: String,

    @SerializedName("lname")
    val lname: String
)
