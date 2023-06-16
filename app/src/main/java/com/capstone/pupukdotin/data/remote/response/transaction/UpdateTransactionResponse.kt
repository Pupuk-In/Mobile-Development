package com.capstone.pupukdotin.data.remote.response.transaction

import com.google.gson.annotations.SerializedName

data class UpdateTransactionResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("transaction")
	val transaction: Any? = null
)
