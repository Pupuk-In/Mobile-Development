package com.capstone.pupukdotin.data.remote.network

import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.AuthenticationCheckResponse
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.DetailProfileResponse
import com.capstone.pupukdotin.data.remote.response.FertilizerTypeResponse
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

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

    @Headers("No-Authentication: true")
    @GET("/api/stores/items/{id}")
    suspend fun getDetailItem(
        @Path("id") idItem: Int
    ) : Response<DetailItemResponse>

    @Headers("No-Authentication: true")
    @GET("/api/home/types")
    suspend fun getTypes() : Response<FertilizerTypeResponse>

    @Headers("No-Authentication: true")
    @GET("/api/home/plants")
    suspend fun getPlants() : Response<PlantResponse>

    @Headers("No-Authentication: true")
    @POST("/api/search/items")
    suspend fun getSearchResult(
        @Body payload: SearchItemsPayload
    ) : Response<SearchItemsResponse>

    @GET("/api/index")
    suspend fun authCheck(): Response<AuthenticationCheckResponse>

    @GET("/api/profile")
    suspend fun getDetailProfile(): Response<DetailProfileResponse>

    @GET("/api/logout")
    suspend fun authLogout(): Response<BasicResponse>
}