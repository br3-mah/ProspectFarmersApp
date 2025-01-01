package com.example.prospectfarmersapp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prospectfarmersapp.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import retrofit2.HttpException

class FarmersViewModel : ViewModel() {
    private val _farmerStats = MutableStateFlow<FarmerStats?>(null)
    val farmerStats: StateFlow<FarmerStats?> get() = _farmerStats

    private val _recentFarmers = MutableStateFlow<List<FarmersResponse>>(emptyList())
    val recentFarmers: StateFlow<List<FarmersResponse>> get() = _recentFarmers

    private val apiService = RetrofitInstance.apiService  // Use ApiClient to access ApiService

    init {
        fetchFarmerStats()
        fetchRecentFarmers()
    }

    private fun fetchFarmerStats() {
        viewModelScope.launch {
            try {
                val response = apiService.getFarmStats()
                Log.d("API_FETCH", "Response Code: ${response.code()}, Response Body: ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _farmerStats.value = FarmerStats(
                            totalUsers = it.totalUsers,
                            totalFarmers = it.totalFarmers,
                            totalProspects = it.totalProspects
                        )
                        Log.d("API_SUCCESS", "Parsed Stats: $_farmerStats")
                    }
                } else {
                    Log.e("API_ERROR", "Failed with Code: ${response.code()}, Message: ${response.message()}")
                    _farmerStats.value = null
                }
            } catch (e: HttpException) {
                Log.e("API_EXCEPTION", "HttpException: ${e.message}")
                _farmerStats.value = null
            } catch (e: Throwable) {
                Log.e("API_EXCEPTION", "Throwable: ${e.localizedMessage}")
                _farmerStats.value = null
            }
        }
    }


    private fun fetchRecentFarmers() {
        viewModelScope.launch {
            try {
                val response = apiService.getRecentFarmers()
                if (response.isSuccessful) {
                    // Correctly map the response to the _recentFarmers
                    _recentFarmers.value = response.body()?.farmers ?: emptyList()
                } else {
                    // Handle failure (e.g., show error message)
                    _recentFarmers.value = emptyList()
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions
                _recentFarmers.value = emptyList()
            } catch (e: Throwable) {
                // Handle other errors (e.g., no internet connection)
                _recentFarmers.value = emptyList()
            }
        }
    }

}
