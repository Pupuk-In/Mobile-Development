package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.carts.AddEditCartPayload
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.payload.wishlist.AddWishlistPayload
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse
import com.capstone.pupukdotin.data.remote.response.wishlist.AddWishlistItemResponse
import com.capstone.pupukdotin.data.remote.response.wishlist.WishlistResponse

class FertilizerRepository(
    private val apiServices: ApiServices
) {



    private val _types = MutableLiveData<NetworkResult<TypeResponse>>()
    val types: LiveData<NetworkResult<TypeResponse>> = _types

    private val _plants = MutableLiveData<NetworkResult<PlantResponse>>()
    val plants: LiveData<NetworkResult<PlantResponse>> = _plants

    private val _searchItem = MutableLiveData<NetworkResult<SearchItemsResponse>>()
    val searchItem: LiveData<NetworkResult<SearchItemsResponse>> = _searchItem


    suspend fun getCartItems(_cartItem : MutableLiveData<NetworkResult<CartItemsResponse>>) {
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

    suspend fun addCartItems(payload: AddEditCartPayload, _addCartMessage: MutableLiveData<NetworkResult<String>>) {
        _addCartMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.addCartItems(payload)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _addCartMessage.value = NetworkResult.Success(responseBody.message ?: "")
            }
        } catch (e: Exception) {
            _addCartMessage.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun editCartItems(payload: AddEditCartPayload,idItem: Int, _editCartMessage: MutableLiveData<NetworkResult<String>>) {
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

    suspend fun deleteCartItems(idItem: Int, _deleteCartMessage: MutableLiveData<NetworkResult<BasicResponse>>) {
        _deleteCartMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.deleteCartItems(idItem)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _deleteCartMessage.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _deleteCartMessage.value = NetworkResult.Error(e.message.toString())
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

    suspend fun getDetailItem(id: Int, _detailItem: MutableLiveData<NetworkResult<DetailItemResponse>>) {
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

    suspend fun searchWishlistItem(payload: SearchItemsPayload, _searchWishlistItems: MutableLiveData<NetworkResult<WishlistResponse>>) {
        _searchWishlistItems.value = NetworkResult.Loading
        try {
            val result = apiServices.getSearchWishlist(payload)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _searchWishlistItems.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _searchWishlistItems.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun addWishlistItem(payload: AddWishlistPayload, _addedWishlistItems: MutableLiveData<NetworkResult<AddWishlistItemResponse>>) {
        _addedWishlistItems.value = NetworkResult.Loading
        try {
            val result = apiServices.createWishlistItem(payload)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _addedWishlistItems.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _addedWishlistItems.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun deleteWishlistItem(idItem: Int, _deletedWishlistMessage: MutableLiveData<NetworkResult<BasicResponse>>) {
        _deletedWishlistMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.deleteWishlistItem(idItem)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _deletedWishlistMessage.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _deletedWishlistMessage.value = NetworkResult.Error(e.message.toString())
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
