package com.capstone.pupukdotin.data.remote.response.common

import com.google.gson.annotations.SerializedName

data class PivotItem(

    @field:SerializedName("item_id")
    val itemId: Int? = null,

    @field:SerializedName("plant_part_id")
    val plantPartId: Int? = null,

    @field:SerializedName("plant_id")
    val plantId: Int? = null
)
