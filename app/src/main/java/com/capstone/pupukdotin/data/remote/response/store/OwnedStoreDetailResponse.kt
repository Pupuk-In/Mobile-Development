package com.capstone.pupukdotin.data.remote.response.store

import com.capstone.pupukdotin.data.remote.response.common.Store
import com.google.gson.annotations.SerializedName

data class OwnedStoreDetailResponse(
	@field:SerializedName("store")
	val store: Store? = null,

	@field:SerializedName("message")
	val message: String? = null
)

