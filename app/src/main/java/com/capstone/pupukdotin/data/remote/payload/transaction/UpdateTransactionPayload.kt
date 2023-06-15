package com.capstone.pupukdotin.data.remote.payload.transaction

import com.google.gson.annotations.SerializedName

data class UpdateTransactionPayload(

	@field:SerializedName("transaction_status_id")
	val transactionStatusId: Int
)
