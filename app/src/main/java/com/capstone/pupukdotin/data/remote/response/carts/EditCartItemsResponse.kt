package com.capstone.pupukdotin.data.remote.response.carts

import com.google.gson.annotations.SerializedName

data class EditCartItemsResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("cart")
	val cart: Cart? = null
) {
	data class Item(

		@field:SerializedName("store_id")
		val storeId: Int? = null,

		@field:SerializedName("sold")
		val sold: Int? = null,

		@field:SerializedName("type_id")
		val typeId: Int? = null,

		@field:SerializedName("rating")
		val rating: String? = null,

		@field:SerializedName("description")
		val description: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("deleted_at")
		val deletedAt: Any? = null,

		@field:SerializedName("relevance")
		val relevance: Any? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("price")
		val price: Int? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("stock")
		val stock: Int? = null,

		@field:SerializedName("brand")
		val brand: String? = null
	)

	data class Cart(

		@field:SerializedName("cart_id")
		val cartId: Int? = null,

		@field:SerializedName("item")
		val item: Item? = null,

		@field:SerializedName("quantity")
		val quantity: Int? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("item_id")
		val itemId: Int? = null,

		@field:SerializedName("price")
		val price: Int? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null
	)
}


