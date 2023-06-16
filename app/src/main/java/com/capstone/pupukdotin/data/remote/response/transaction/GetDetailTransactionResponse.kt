package com.capstone.pupukdotin.data.remote.response.transaction

import com.google.gson.annotations.SerializedName

data class GetDetailTransactionResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("transaction_detail")
	val transactionDetail: TransactionDetail
) {
	data class ItemHistoryItem(

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("plant_part")
		val plantPart: List<String>,

		@field:SerializedName("price")
		val price: Int,

		@field:SerializedName("plant")
		val plant: List<String>,

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("description")
		val description: String,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("type")
		val type: String,

		@field:SerializedName("brand")
		val brand: String,

		@field:SerializedName("picture")
		val picture: List<String>,

		@field:SerializedName("transaction_item_id")
		val transactionItemId: Int
	)

	data class TransactionItemItem(

		@field:SerializedName("store_id")
		val storeId: Int,

		@field:SerializedName("item_history")
		val itemHistory: List<ItemHistoryItem>,

		@field:SerializedName("transaction_by_store_id")
		val transactionByStoreId: Int,

		@field:SerializedName("quantity")
		val quantity: Int,

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("item_id")
		val itemId: Int,

		@field:SerializedName("price")
		val price: Int,

		@field:SerializedName("subtotal")
		val subtotal: Int,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("id")
		val id: Int
	)

	data class PaymentStatus(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int
	)

	data class Transaction(

		@field:SerializedName("payment_status_id")
		val paymentStatusId: Int,

		@field:SerializedName("payment_method_id")
		val paymentMethodId: Int,

		@field:SerializedName("payment_status")
		val paymentStatus: PaymentStatus,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("recipient_address")
		val recipientAddress: String,

		@field:SerializedName("recipient_name")
		val recipientName: String,

		@field:SerializedName("payment_method")
		val paymentMethod: PaymentMethod,

		@field:SerializedName("recipient_phone")
		val recipientPhone: String
	)

	data class PaymentMethod(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int
	)

	data class TransactionStatus(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int
	)

	data class TransactionDetail(

		@field:SerializedName("transaction_id")
		val transactionId: Int,

		@field:SerializedName("store_id")
		val storeId: Int,

		@field:SerializedName("total")
		val total: Int,

		@field:SerializedName("transaction_status")
		val transactionStatus: TransactionStatus,

		@field:SerializedName("updated_at")
		val updatedAt: String,

		@field:SerializedName("transaction_status_id")
		val transactionStatusId: Int,

		@field:SerializedName("transaction_item")
		val transactionItem: List<TransactionItemItem>,

		@field:SerializedName("created_at")
		val createdAt: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("invoice")
		val invoice: String,

		@field:SerializedName("transaction")
		val transaction: Transaction
	)
}


