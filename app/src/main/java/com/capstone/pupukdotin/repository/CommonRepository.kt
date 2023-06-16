package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.CommonServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.google.gson.Gson
import okhttp3.MultipartBody

class CommonRepository(private val apiServices: CommonServices) {

    suspend fun uploadImage(file: MultipartBody.Part, _fileUploaded: MutableLiveData<NetworkResult<UploadImagesResponse>>) {
        _fileUploaded.value = NetworkResult.Loading
        try {
            val result = apiServices.uploadImagesToServer(file)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _fileUploaded.value = NetworkResult.Success(responseBody)
                }
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _fileUploaded.value = NetworkResult.Error(mapper.message)
                    Log.e("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _fileUploaded.value = NetworkResult.Error(result.message())
                    Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _fileUploaded.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    companion object {
        @Volatile
        private var instance: CommonRepository? = null

        fun getInstance(
            apiServices: CommonServices
        ): CommonRepository =
            instance ?: synchronized(this) {
                CommonRepository(apiServices).apply {
                    instance = this
                }
            }
    }
}