package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.carts.AddEditCartPayload
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
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

    private val _cartItem = MutableLiveData<NetworkResult<CartItemsResponse>>()
    val cartItem: LiveData<NetworkResult<CartItemsResponse>> = _cartItem

    private val _editCartMessage = MutableLiveData<NetworkResult<String>>()
    val editCartMessage: LiveData<NetworkResult<String>> get() = _editCartMessage


    suspend fun getCartItems() {
        _cartItem.value = NetworkResult.Loading
        try {
            val result = apiServices.getCartItems()
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _cartItem.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _cartItem.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun editCartItems(payload: AddEditCartPayload,idItem: Int) {
        _editCartMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.editCartItems(payload, idItem)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _editCartMessage.value = NetworkResult.Success(responseBody.message ?: "")
            }
        } catch (e: Exception) {
            _editCartMessage.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

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

    suspend fun getAllPlants(_allPlants: MutableLiveData<NetworkResult<PlantResponse>>) {
        _allPlants.value = NetworkResult.Loading
        try {
            val result = apiServices.getAllPlants()
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _allPlants.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _allPlants.value = NetworkResult.Error(e.message.toString())
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
