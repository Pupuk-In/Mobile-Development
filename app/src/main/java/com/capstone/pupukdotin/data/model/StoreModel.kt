package com.capstone.pupukdotin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreModel(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("picture")
	val picture: String,
) : Parcelable
