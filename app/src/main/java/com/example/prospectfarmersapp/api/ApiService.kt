package com.example.prospectfarmersapp.api

import com.example.prospectfarmersapp.models.AddFarmerRequest
import retrofit2.http.GET
import retrofit2.Response
import com.example.prospectfarmersapp.models.StatsResponse // Assuming you have this class
import com.example.prospectfarmersapp.models.RecentFarmersResponse // Assuming you have this class
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Endpoint for fetching farm stats
    @GET("api/stats")
    suspend fun getFarmStats(): Response<StatsResponse>

    // Endpoint for fetching recent farmers
    @GET("api/recent-farmers")
    suspend fun getRecentFarmers(): Response<RecentFarmersResponse>

    // Endpoint for posting farmers
    @POST("api/farmer")
    suspend fun addFarmer(@Body farmerRequest: AddFarmerRequest): Response<Unit>
}