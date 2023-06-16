package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
) {
	data class User(

		@field:SerializedName("access_token")
		val accessToken: String,

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("email_verified_at")
		val emailVerifiedAt: String,

		@field:SerializedName("id")
		val id: String,

		@field:SerializedName("email")
		val email: String
	)
}


