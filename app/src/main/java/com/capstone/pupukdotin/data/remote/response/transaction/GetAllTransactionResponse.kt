package com.capstone.pupukdotin.data.remote.response.transaction

import com.google.gson.annotations.SerializedName

data class GetAllTransactionResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("transaction")
	val transaction: List<TransactionItem>
) {
	data class TransactionItem(

		@field:SerializedName("transaction_id")
		val transactionId: Int,

		@field:SerializedName("store_id")
		val storeId: Int,

		@field:SerializedName("total")
		val total: Int,

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("transaction_status_id")
		val transactionStatusId: Int,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("invoice")
		val invoice: String,

		@field:SerializedName("store")
		val store: String,

		@field:SerializedName("status")
		val status: String
	)
}


