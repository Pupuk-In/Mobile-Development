package com.capstone.pupukdotin.data.remote.network

import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CommonServices {
    @Multipart
    @POST("/api/images")
    suspend fun uploadImagesToServer(
        @Part file: MultipartBody.Part
    ): Response<UploadImagesResponse>
}