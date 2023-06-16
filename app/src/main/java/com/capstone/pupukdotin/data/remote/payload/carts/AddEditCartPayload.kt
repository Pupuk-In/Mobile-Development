package com.capstone.pupukdotin.data.remote.payload.carts

import com.google.gson.annotations.SerializedName

data class AddEditCartPayload(

	@field:SerializedName("quantity")
	val quantity: Int,

	@field:SerializedName("item_id")
	val itemId: Int? = null
)
