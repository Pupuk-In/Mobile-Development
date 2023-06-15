package com.capstone.pupukdotin.data.remote.payload.transaction

import com.google.gson.annotations.SerializedName

data class CreateTransactionPayload(

	@field:SerializedName("payment_method_id")
	val paymentMethodId: Int,

	@field:SerializedName("recipient_longitude")
	val recipientLongitude: Double,

	@field:SerializedName("recipient_latitude")
	val recipientLatitude: Double,

	@field:SerializedName("recipient_address")
	val recipientAddress: String,

	@field:SerializedName("recipient_name")
	val recipientName: String,

	@field:SerializedName("recipient_phone")
	val recipientPhone: String
)
