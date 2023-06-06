package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResultResponse(

	@field:SerializedName("item")
	val item: Item? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
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

	data class Pivot(

		@field:SerializedName("plant_id")
		val plantId: Int? = null,

		@field:SerializedName("item_id")
		val itemId: Int? = null,

		@field:SerializedName("plant_part_id")
		val plantPartId: Int? = null
	)

	data class LinksItem(

		@field:SerializedName("active")
		val active: Boolean? = null,

		@field:SerializedName("label")
		val label: String? = null,

		@field:SerializedName("url")
		val url: Any? = null
	)

	data class Item(

		@field:SerializedName("per_page")
		val perPage: Int? = null,

		@field:SerializedName("data")
		val data: List<DataItem?>? = null,

		@field:SerializedName("last_page")
		val lastPage: Int? = null,

		@field:SerializedName("next_page_url")
		val nextPageUrl: String? = null,

		@field:SerializedName("prev_page_url")
		val prevPageUrl: Any? = null,

		@field:SerializedName("first_page_url")
		val firstPageUrl: String? = null,

		@field:SerializedName("path")
		val path: String? = null,

		@field:SerializedName("total")
		val total: Int? = null,

		@field:SerializedName("last_page_url")
		val lastPageUrl: String? = null,

		@field:SerializedName("from")
		val from: Int? = null,

		@field:SerializedName("links")
		val links: List<LinksItem?>? = null,

		@field:SerializedName("to")
		val to: Int? = null,

		@field:SerializedName("current_page")
		val currentPage: Int? = null
	)

	data class DataItem(

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

		@field:SerializedName("relevance")
		val relevance: Any? = null,

		@field:SerializedName("picture")
		val picture: List<PictureItem?>? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("price")
		val price: Int? = null,

		@field:SerializedName("plant")
		val plant: List<PlantItem?>? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("stock")
		val stock: Int? = null,

		@field:SerializedName("brand")
		val brand: String? = null
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
}


