package com.capstone.pupukdotin.data.remote.response.common

import com.google.gson.annotations.SerializedName

data class LinksItem(

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)
