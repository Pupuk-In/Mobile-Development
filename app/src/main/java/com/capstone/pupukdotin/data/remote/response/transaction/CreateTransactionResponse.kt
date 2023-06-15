package com.capstone.pupukdotin.data.remote.response.transaction

import com.capstone.pupukdotin.data.remote.response.common.Store
import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("transaction")
	val transaction: Transaction
)

data class TransactionByStoreItem(

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

	@field:SerializedName("transaction_item")
	val transactionItem: List<TransactionItem>,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("invoice")
	val invoice: String,

	@field:SerializedName("store")
	val store: Store
)



data class Transaction(

	@field:SerializedName("recipient_longitude")
	val recipientLongitude: String,

	@field:SerializedName("recipient_latitude")
	val recipientLatitude: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("recipient_address")
	val recipientAddress: String,

	@field:SerializedName("payment_status_id")
	val paymentStatusId: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("payment_method_id")
	val paymentMethodId: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("profile_id")
	val profileId: Int,

	@field:SerializedName("transaction_by_store")
	val transactionByStore: List<TransactionByStoreItem>,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("recipient_name")
	val recipientName: String,

	@field:SerializedName("recipient_phone")
	val recipientPhone: String
)

data class Item(

	@field:SerializedName("store_id")
	val storeId: Int,

	@field:SerializedName("sold")
	val sold: Int,

	@field:SerializedName("type_id")
	val typeId: Int,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("deleted_at")
	val deletedAt: Any,

	@field:SerializedName("relevance")
	val relevance: Any,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("brand")
	val brand: String
)

data class TransactionItem(

	@field:SerializedName("store_id")
	val storeId: Int,

	@field:SerializedName("item")
	val item: Item,

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
