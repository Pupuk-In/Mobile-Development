package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("errors")
	val errors: Errors? = null
) {
	data class Errors(

		@field:SerializedName("email")
		val email: List<String>? = null,

		@field:SerializedName("username")
		val username: List<String>? = null
	)
}


