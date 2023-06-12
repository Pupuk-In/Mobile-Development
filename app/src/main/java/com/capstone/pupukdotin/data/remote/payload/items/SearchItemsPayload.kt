package com.capstone.pupukdotin.data.remote.payload.items

import com.google.gson.annotations.SerializedName

data class SearchItemsPayload(

	@field:SerializedName("search")
	val search: String? = null,

	@field:SerializedName("perPage")
	val perPage: Int? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("plant")
	val plant: Int? = null,

	@field:SerializedName("part")
	val part: Int? = null,

	@field:SerializedName("sort")
	val sort: String? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("order")
	val order: String? = null
)
