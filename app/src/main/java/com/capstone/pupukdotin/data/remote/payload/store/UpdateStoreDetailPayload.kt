package com.capstone.pupukdotin.data.remote.payload.store

import com.google.gson.annotations.SerializedName

data class UpdateStoreDetailPayload(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)
