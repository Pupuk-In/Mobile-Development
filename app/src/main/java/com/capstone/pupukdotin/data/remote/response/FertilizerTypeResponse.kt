package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class FertilizerTypeResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("type")
	val type: List<TypeItem>
)

data class TypeItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String
)


