package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse

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
            }
        } catch (e: Exception) {
            _ownedDetailStore.value = NetworkResult.Error(e.message.toString())
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
            }
        } catch (e: Exception) {
            _editStoreMessage.value = NetworkResult.Error(e.message.toString())
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