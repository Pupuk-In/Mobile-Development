package com.capstone.pupukdotin.data.remote.response.ai

import com.google.gson.annotations.SerializedName

data class NutritionDetectionResponse(

	@field:SerializedName("confidence")
	val confidence: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("class")
	val jsonMemberClass: String
)
