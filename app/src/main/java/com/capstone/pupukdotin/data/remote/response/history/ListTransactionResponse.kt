package com.capstone.pupukdotin.data.remote.response.history

import com.capstone.pupukdotin.data.remote.response.common.DataItem
import com.google.gson.annotations.SerializedName

data class ListTransactionResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class DataItem(

		@field:SerializedName("transactions")
		val transactions: List<TransactionsItem?>? = null,

		@field:SerializedName("status")
		val status: String? = null
	)

	data class TransactionsItem(

		@field:SerializedName("transaction_id")
		val transactionId: Int? = null,

		@field:SerializedName("store_id")
		val storeId: Int? = null,

		@field:SerializedName("total")
		val total: Int? = null,

		@field:SerializedName("updated_at")
		val updatedAt: String? = null,

		@field:SerializedName("transaction_status_id")
		val transactionStatusId: Int? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("invoice")
		val invoice: String? = null,

		@field:SerializedName("recipient_name")
		val recipientName: String? = null
	)
}


