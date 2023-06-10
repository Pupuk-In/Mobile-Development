package com.capstone.pupukdotin.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class ProfileDetailResponse(

	@field:SerializedName("profile")
	val profile: Profile? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class Profile(

		@field:SerializedName("address")
		val address: String? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("user_id")
		val userId: Int? = null,

		@field:SerializedName("birth_date")
		val birthDate: String? = null,

		@field:SerializedName("latitude")
		val latitude: Double? = null,

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
		val longitude: Double? = null,

		@field:SerializedName("username")
		val username: String? = null
	)
}


