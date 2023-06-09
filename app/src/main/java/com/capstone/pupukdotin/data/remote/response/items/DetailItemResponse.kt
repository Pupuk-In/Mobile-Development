package com.capstone.pupukdotin.data.remote.response.items

import com.google.gson.annotations.SerializedName

data class DetailItemResponse(

	@field:SerializedName("item")
	val item: Item? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class PlantItem(

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("pivot")
		val pivot: Pivot? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("picture")
		val picture: String? = null
	)

	data class Pivot(

		@field:SerializedName("item_id")
		val itemId: Int? = null,

		@field:SerializedName("plant_part_id")
		val plantPartId: Int? = null,

		@field:SerializedName("plant_id")
		val plantId: Int? = null
	)

	data class PlantPartItem(

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("pivot")
		val pivot: Pivot? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("picture")
		val picture: String? = null
	)

	data class Item(

		@field:SerializedName("store_id")
		val storeId: Int? = null,

		@field:SerializedName("sold")
		val sold: Int? = null,

		@field:SerializedName("type_id")
		val typeId: Int? = null,

		@field:SerializedName("plant_part")
		val plantPart: List<PlantPartItem>? = null,

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
		val deletedAt: String? = null,

		@field:SerializedName("relevance")
		val relevance: String? = null,

		@field:SerializedName("picture")
		val picture: List<PictureItem>? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("price")
		val price: Int? = null,

		@field:SerializedName("plant")
		val plant: List<PlantItem>? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("stock")
		val stock: Int? = null,

		@field:SerializedName("brand")
		val brand: String? = null
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

	data class PictureItem(

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("item_id")
		val itemId: Int? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("picture")
		val picture: String? = null
	)

	data class Store(

		@field:SerializedName("address")
		val address: String? = null,

		@field:SerializedName("distance")
		val distance: String? = null,

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
}


