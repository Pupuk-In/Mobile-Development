package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class FertilizerPlantResponse(

	@field:SerializedName("plant")
	val plant: List<PlantItem>,

	@field:SerializedName("message")
	val message: String
)
