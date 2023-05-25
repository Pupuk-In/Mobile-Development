package com.capstone.pupukdotin.data.remote.network

import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {

    @Headers("No-Authentication: true")
    @POST("/api/signup")
    suspend fun authRegister(
        @Body payload: RegisterPayload
    ): Response<RegisterResponse>

    @Headers("No-Authentication: true")
    @POST("/api/login")
    suspend fun authLogin(
        @Body payload: LoginPayload
    ): Response<LoginResponse>
}