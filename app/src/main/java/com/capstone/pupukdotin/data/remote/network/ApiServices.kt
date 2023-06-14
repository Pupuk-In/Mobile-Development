package com.capstone.pupukdotin.data.remote.network

import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.payload.carts.AddEditCartPayload
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.payload.profile.UpdateProfilePayload
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.payload.wishlist.AddWishlistPayload
import com.capstone.pupukdotin.data.remote.response.AuthenticationCheckResponse
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.data.remote.response.carts.EditCartItemsResponse
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreAllItemsResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.data.remote.response.wishlist.AddWishlistItemResponse
import com.capstone.pupukdotin.data.remote.response.wishlist.WishlistResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun getTypes() : Response<TypeResponse>

    @Headers("No-Authentication: true")
    @GET("/api/home/plants")
    suspend fun getPlants() : Response<PlantResponse>

    @Headers("No-Authentication: true")
    @GET("/api/plants")
    suspend fun getAllPlants() : Response<PlantResponse>

    @Headers("No-Authentication: true")
    @POST("/api/search/items")
    suspend fun getSearchResult(
        @Body payload: SearchItemsPayload
    ) : Response<SearchItemsResponse>

    @POST("/api/wishlists/index")
    suspend fun getSearchWishlist(
        @Body payload: SearchItemsPayload
    ) : Response<WishlistResponse>

    @POST("/api/wishlists")
    suspend fun createWishlistItem(
        @Body payload: AddWishlistPayload
    ) : Response<AddWishlistItemResponse>

    @DELETE("/api/wishlists/{id}")
    suspend fun deleteWishlistItem(
        @Path("id") idItem: Int
    ) : Response<BasicResponse>

    @Headers("No-Authentication: true")
    @POST("/api/stores/{id}/catalogs")
    suspend fun getStoreDetail(
        @Body payload: SearchCatalogPayload,
        @Path("id") idStore: Int
    ) : Response<StoreDetailResponse>

    @GET("/api/index")
    suspend fun authCheck(): Response<AuthenticationCheckResponse>

    @GET("/api/carts")
    suspend fun getCartItems(): Response<CartItemsResponse>

    @POST("/api/carts")
    suspend fun addCartItems(
        @Body payload: AddEditCartPayload
    ): Response<EditCartItemsResponse>

    @PATCH("/api/carts/{id}")
    suspend fun editCartItems(
        @Body payload: AddEditCartPayload,
        @Path("id") idItemCart: Int
    ): Response<EditCartItemsResponse>

    @DELETE("/api/carts/{id}")
    suspend fun deleteCartItems(
        @Path("id") idItemCart: Int
    ): Response<BasicResponse>

    @GET("/api/profile")
    suspend fun getDetailProfile(): Response<ProfileDetailResponse>

    @PATCH("/api/profile")
    suspend fun updateDetailProfile(
        @Body payload: UpdateProfilePayload
    ): Response<ProfileDetailResponse>

    @GET("/api/logout")
    suspend fun authLogout(): Response<BasicResponse>

    @Multipart
    @POST("/api/images")
    suspend fun uploadImagesToServer(
        @Part file: MultipartBody.Part
    ): Response<UploadImagesResponse>

    @GET("/api/stores")
    suspend fun getOwnedStoreDetail() : Response<OwnedStoreDetailResponse>

    @PATCH("/api/stores")
    suspend fun editOwnedStoreDetail(
        @Body payload: UpdateStoreDetailPayload
    ) : Response<OwnedStoreDetailResponse>

    @GET("/api/stores/items/getall")
    suspend fun getAllItems() : Response<StoreAllItemsResponse>
}