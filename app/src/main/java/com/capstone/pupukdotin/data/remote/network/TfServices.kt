package com.capstone.pupukdotin.data.remote.network

import com.capstone.pupukdotin.data.remote.response.ai.NutritionDetectionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TfServices {
    @Multipart
    @POST("/predict")
    suspend fun createNutritionDetection(
        @Part file: MultipartBody.Part
    ): Response<NutritionDetectionResponse>
}