package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreAllItemsResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse
import com.google.gson.Gson

class StoreRepository(
    private val apiServices: ApiServices
) {

    suspend fun getDetailStore(
        payload: SearchCatalogPayload,
        idStore: Int,
        _detailStore: MutableLiveData<NetworkResult<StoreDetailResponse>>
    ) {
        _detailStore.value = NetworkResult.Loading
        try {
            val result = apiServices.getStoreDetail(payload, idStore)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _detailStore.value = NetworkResult.Success(responseBody)
            } else {
                _detailStore.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _detailStore.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getOwnedDetailStore(_ownedDetailStore: MutableLiveData<NetworkResult<OwnedStoreDetailResponse>>) {
        _ownedDetailStore.value = NetworkResult.Loading
        try {
            val result = apiServices.getOwnedStoreDetail()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _ownedDetailStore.value =
                    NetworkResult.Success(responseBody)
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _ownedDetailStore.value = NetworkResult.Error(mapper.message)
                    Log.d("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _ownedDetailStore.value = NetworkResult.Error(result.message())
                    Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _ownedDetailStore.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun addNewOwnedStore(payload: UpdateStoreDetailPayload, _createNewStoreMessage: MutableLiveData<NetworkResult<String>>) {
        _createNewStoreMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.createNewOwnedStore(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _createNewStoreMessage.value =
                    NetworkResult.Success(responseBody.message ?:"")
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _createNewStoreMessage.value = NetworkResult.Error(mapper.message)
                    Log.d("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _createNewStoreMessage.value = NetworkResult.Error(result.message())
                    Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _createNewStoreMessage.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun editOwnedDetailStore(payload: UpdateStoreDetailPayload, _editStoreMessage: MutableLiveData<NetworkResult<String>>) {
        _editStoreMessage.value = NetworkResult.Loading
        try {
            val result = apiServices.editOwnedStoreDetail(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _editStoreMessage.value =
                    NetworkResult.Success(responseBody.message ?:"")
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _editStoreMessage.value = NetworkResult.Error(mapper.message)
                    Log.d("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _editStoreMessage.value = NetworkResult.Error(result.message())
                    Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _editStoreMessage.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getAllItems(_allItems: MutableLiveData<NetworkResult<StoreAllItemsResponse>>){
        _allItems.value = NetworkResult.Loading
        try {
            val result = apiServices.getAllItems()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _allItems.value = NetworkResult.Success(responseBody)
            } else {
                _allItems.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _allItems.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    companion object {
        @Volatile
        private var instance: StoreRepository? = null

        fun getInstance(
            apiServices: ApiServices
        ): StoreRepository =
            instance ?: synchronized(this) {
                StoreRepository(apiServices).apply {
                    instance = this
                }
            }
    }
}