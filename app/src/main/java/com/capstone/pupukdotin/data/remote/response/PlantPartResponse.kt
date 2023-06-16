package com.capstone.pupukdotin.data.remote.response

import com.capstone.pupukdotin.data.remote.response.common.PlantPartItem
import com.google.gson.annotations.SerializedName

data class PlantPartResponse(

	@field:SerializedName("plant_part")
	val plantPart: List<PlantPartItem>,

	@field:SerializedName("message")
	val message: String
) {
	data class PlantPartItem(

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
}


