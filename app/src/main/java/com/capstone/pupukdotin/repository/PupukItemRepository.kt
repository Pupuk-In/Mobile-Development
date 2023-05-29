package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.DetailItemResponse

class PupukItemRepository(
    private val apiServices: ApiServices
) {

    private val _detailItem = MutableLiveData<NetworkResult<DetailItemResponse>>()
    val detailItem: LiveData<NetworkResult<DetailItemResponse>> = _detailItem

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

    companion object {
        @Volatile
        private var instance: PupukItemRepository? = null

        fun getInstance(
            apiServices: ApiServices
        ): PupukItemRepository =
            instance ?: synchronized(this) {
                PupukItemRepository(apiServices).apply {
                    instance = this
                }
            }
    }
}
