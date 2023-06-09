package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse

class FertilizerRepository(
    private val apiServices: ApiServices
) {

    private val _detailItem = MutableLiveData<NetworkResult<DetailItemResponse>>()
    val detailItem: LiveData<NetworkResult<DetailItemResponse>> = _detailItem

    private val _types = MutableLiveData<NetworkResult<TypeResponse>>()
    val types: LiveData<NetworkResult<TypeResponse>> = _types

    private val _plants = MutableLiveData<NetworkResult<PlantResponse>>()
    val plants: LiveData<NetworkResult<PlantResponse>> = _plants

    private val _searchItem = MutableLiveData<NetworkResult<SearchItemsResponse>>()
    val searchItem: LiveData<NetworkResult<SearchItemsResponse>> = _searchItem


    suspend fun getTypes() {
        _types.value = NetworkResult.Loading
        try {
            val result = apiServices.getTypes()
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _types.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _types.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getPlants() {
        _plants.value = NetworkResult.Loading
        try {
            val result = apiServices.getPlants()
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _plants.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _plants.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getDetailItem(id: Int) {
        _detailItem.value = NetworkResult.Loading
        try {
            val result = apiServices.getDetailItem(id)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _detailItem.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _detailItem.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun searchItem(payload: SearchItemsPayload) {
        _searchItem.value = NetworkResult.Loading
        try {
            val result = apiServices.getSearchResult(payload)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _searchItem.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _searchItem.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    companion object {
        @Volatile
        private var instance: FertilizerRepository? = null

        fun getInstance(
            apiServices: ApiServices
        ): FertilizerRepository =
            instance ?: synchronized(this) {
                FertilizerRepository(apiServices).apply {
                    instance = this
                }
            }
    }
}
