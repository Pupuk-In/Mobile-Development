package com.capstone.pupukdotin.data.remote.response

import com.capstone.pupukdotin.data.remote.response.common.TypeItem
import com.google.gson.annotations.SerializedName

data class TypeResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("type")
	val type: List<TypeItem>?
)

