package com.capstone.pupukdotin.data.remote.response.store

import com.capstone.pupukdotin.data.remote.response.common.PictureItem
import com.capstone.pupukdotin.data.remote.response.common.PlantItem
import com.capstone.pupukdotin.data.remote.response.common.PlantPartItem
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.capstone.pupukdotin.data.remote.response.common.TypeItem
import com.google.gson.annotations.SerializedName

data class StoreItemResponse(

	@field:SerializedName("Item")
	val item: Item? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
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
		val type: TypeItem? = null,

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
}
