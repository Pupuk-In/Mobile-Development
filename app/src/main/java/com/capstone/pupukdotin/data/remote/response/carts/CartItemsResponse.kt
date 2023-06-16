package com.capstone.pupukdotin.data.remote.response.carts

import com.capstone.pupukdotin.data.remote.response.common.PictureItem
import com.capstone.pupukdotin.data.remote.response.common.Store
import com.google.gson.annotations.SerializedName


data class CartItemsResponse(
	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("cart")
	val cart: Cart? = null
) {
	data class Cart(

		@field:SerializedName("total")
		val total: Int? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("profile_id")
		val profileId: Int? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("cart_item")
		val cartItem: List<CartItem>? = null
	)

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

		@field:SerializedName("store")
		val store: Store? = null,

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

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("stock")
		val stock: Int? = null,

		@field:SerializedName("brand")
		val brand: String? = null
	)

	data class CartItem(

		@field:SerializedName("cart_id")
		val cartId: Int? = null,

		@field:SerializedName("item")
		val item: Item? = null,

		@field:SerializedName("quantity")
		var quantity: Int? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("item_id")
		val itemId: Int? = null,

		@field:SerializedName("price")
		var price: Int? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null
	)
}



