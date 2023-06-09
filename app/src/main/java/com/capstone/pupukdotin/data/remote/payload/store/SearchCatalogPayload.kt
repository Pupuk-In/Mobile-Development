package com.capstone.pupukdotin.data.remote.payload.store

import com.google.gson.annotations.SerializedName

data class SearchCatalogPayload(

	@field:SerializedName("store_id")
	val storeId: Int,

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
