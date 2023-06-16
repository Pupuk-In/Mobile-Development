package com.capstone.pupukdotin.data.remote.payload.wishlist

import com.google.gson.annotations.SerializedName

data class AddWishlistPayload(

	@field:SerializedName("item_id")
	val itemId: Int
)
