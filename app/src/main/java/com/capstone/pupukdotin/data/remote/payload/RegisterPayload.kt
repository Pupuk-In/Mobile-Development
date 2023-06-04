package com.capstone.pupukdotin.data.remote.payload

import com.google.gson.annotations.SerializedName

data class RegisterPayload(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
