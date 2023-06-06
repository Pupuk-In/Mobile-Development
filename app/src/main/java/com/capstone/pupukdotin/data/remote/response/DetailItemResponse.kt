package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailItemResponse(

	@field:SerializedName("item")
	val item: Item? = null,

	@field:SerializedName("message")
	val message: String
)

data class PictureItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("item_id")
	val itemId: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String
)

data class Store(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("profile_id")
	val profileId: Int,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("longitude")
	val longitude: String
)

data class Pivot(

	@field:SerializedName("item_id")
	val itemId: Int,

	@field:SerializedName("plant_part_id")
	val plantPartId: Int,

	@field:SerializedName("plant_id")
	val plantId: Int
)

data class Type(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String
)

data class PlantItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("pivot")
	val pivot: Pivot,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String
)

data class Item(

	@field:SerializedName("store_id")
	val storeId: Int,

	@field:SerializedName("sold")
	val sold: Int,

	@field:SerializedName("type_id")
	val typeId: Int,

	@field:SerializedName("plant_part")
	val plantPart: List<PlantPartItem>,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("store")
	val store: Store,

	@field:SerializedName("type")
	val type: Type,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("relevance")
	val relevance: Any? = null,

	@field:SerializedName("picture")
	val picture: List<PictureItem>? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("plant")
	val plant: List<PlantItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("brand")
	val brand: String
)

data class PlantPartItem(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("pivot")
	val pivot: Pivot,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("picture")
	val picture: String
)
