package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.network.TfServices
import com.capstone.pupukdotin.data.remote.response.ai.NutritionDetectionResponse
import okhttp3.MultipartBody

class MachineLearningRepository(private val apiServices: TfServices) {

    suspend fun detectNutritionImage(
        file: MultipartBody.Part,
        _detectionResult: MutableLiveData<NetworkResult<NutritionDetectionResponse>>
    ) {
        _detectionResult.value = NetworkResult.Loading
        try {
            val result = apiServices.createNutritionDetection(file)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _detectionResult.value = NetworkResult.Success(responseBody)
                }
            } else {
                _detectionResult.value = NetworkResult.Error(result.message())
                Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _detectionResult.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }

    }

    companion object {
        @Volatile
        private var instance: MachineLearningRepository? = null

        fun getInstance(
            apiServices: TfServices
        ): MachineLearningRepository =
            instance ?: synchronized(this) {
                MachineLearningRepository(apiServices).apply {
                    instance = this
                }
            }
    }

}