package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailItemResponse(

	@field:SerializedName("item")
	val item: List<ItemItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ItemItem(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("sold")
	val sold: Int? = null,

	@field:SerializedName("type_id")
	val typeId: Int? = null,

	@field:SerializedName("plant_part")
	val plantPart: List<Any?>? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("store")
	val store: Store? = null,

	@field:SerializedName("type")
	val type: Type? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("relevance")
	val relevance: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("plant")
	val plant: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("brand")
	val brand: String? = null
)

data class Store(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("profile_id")
	val profileId: Int? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class Type(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)
