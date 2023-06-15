package com.capstone.pupukdotin.data.remote.payload.items

import com.google.gson.annotations.SerializedName

data class CreateNewItemPayload(

	@field:SerializedName("plant_id")
	val plantId: List<Int>? = null,

	@field:SerializedName("type_id")
	val typeId: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("plant_part_id")
	val plantPartId: List<Int>? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("picture")
	val picture: List<String>? = null,

	@field:SerializedName("relevance")
	val relevance: String? = null
)
