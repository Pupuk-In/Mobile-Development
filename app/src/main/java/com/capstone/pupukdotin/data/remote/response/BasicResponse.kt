package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class BasicResponse(

	@field:SerializedName("message")
	val message: String
)
