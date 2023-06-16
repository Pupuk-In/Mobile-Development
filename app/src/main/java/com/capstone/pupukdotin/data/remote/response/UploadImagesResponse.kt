package com.capstone.pupukdotin.data.remote.response

import com.google.gson.annotations.SerializedName
import java.io.File

data class UploadImagesResponse(

	@field:SerializedName("file")
	val file: File? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class File(

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("url")
		val url: String? = null
	)
}


