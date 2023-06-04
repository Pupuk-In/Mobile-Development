package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProfileResponse(

	@field:SerializedName("profile")
	val profile: Profile? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Profile(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("birth_date")
	val birthDate: Any? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)
