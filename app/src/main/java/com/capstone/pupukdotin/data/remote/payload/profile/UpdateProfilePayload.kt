package com.capstone.pupukdotin.data.remote.payload.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfilePayload(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("birth_date")
	val birthDate: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)
