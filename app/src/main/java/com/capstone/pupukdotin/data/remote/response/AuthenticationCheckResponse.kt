package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthenticationCheckResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
