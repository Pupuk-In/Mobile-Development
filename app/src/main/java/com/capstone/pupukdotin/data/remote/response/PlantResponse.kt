package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlantResponse(

	@field:SerializedName("plant")
	val plant: List<PlantItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class PlantItem(

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("picture")
		val picture: String? = null
	)
}


