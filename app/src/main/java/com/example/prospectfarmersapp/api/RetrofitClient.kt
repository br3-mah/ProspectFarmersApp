package com.example.prospectfarmersapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://admin.flapabay.com/internal/public/"

    // Configure logging interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Configure OkHttpClient with logging and timeout settings
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS) // Adjust connection timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Adjust read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Adjust write timeout
        .build()

    // Create the Retrofit instance
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Pass OkHttpClient to Retrofit
            .build()
            .create(ApiService::class.java)
    }
}
