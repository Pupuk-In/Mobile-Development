package com.capstone.pupukdotin.data.remote.response.common

import com.google.gson.annotations.SerializedName

data class Store(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("distance")
    val distance: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("profile_id")
    val profileId: Int? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("picture")
    val picture: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null
)
