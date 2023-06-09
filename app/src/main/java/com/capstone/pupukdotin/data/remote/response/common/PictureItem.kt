package com.capstone.pupukdotin.data.remote.response.common

import com.google.gson.annotations.SerializedName

data class PictureItem(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("item_id")
    val itemId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("picture")
    val picture: String? = null
)
