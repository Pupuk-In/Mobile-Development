package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse

class StoreRepository(
    private val apiServices: ApiServices
) {

    private val _detailStore = MutableLiveData<NetworkResult<StoreDetailResponse>>()
    val detailStore: LiveData<NetworkResult<StoreDetailResponse>> = _detailStore

    suspend fun getDetailStore(payload: SearchCatalogPayload, idStore: Int) {
        _detailStore.value = NetworkResult.Loading
        try {
            val result = apiServices.getStoreDetail(payload,idStore)
            if(result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _detailStore.value = NetworkResult.Success(responseBody)
            }
        } catch (e: Exception) {
            _detailStore.value = NetworkResult.Error(e.message.toString())
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