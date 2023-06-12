package com.capstone.pupukdotin.data.remote.response.common

import com.google.gson.annotations.SerializedName

data class PlantPartItem(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("pivot")
    val pivotItem: PivotItem? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("picture")
    val picture: String? = null
)
