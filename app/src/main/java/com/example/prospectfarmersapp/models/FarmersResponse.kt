

package com.example.prospectfarmersapp.models

data class RecentFarmersResponse(
    val farmers: List<FarmersResponse>
)

data class FarmersResponse(
    val id: Int,
    val fName: String,
    val lName: String,
    val farmName: String,
    val location: String,
    val joinedDate: String
)