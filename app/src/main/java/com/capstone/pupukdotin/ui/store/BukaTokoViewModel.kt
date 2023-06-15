package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.repository.StoreRepository
import com.capstone.pupukdotin.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class BukaTokoViewModel(
    private val repository: StoreRepository,
    private val commonRepository: CommonRepository
) : ViewModel() {
    private val _fileUploaded = MutableLiveData<NetworkResult<UploadImagesResponse>>()
    val fileUploaded: LiveData<NetworkResult<UploadImagesResponse>> = _fileUploaded

    private val _createNewStoreMessage = MutableLiveData<NetworkResult<String>>()
    val createNewStoreMessage: LiveData<NetworkResult<String>> get() = _createNewStoreMessage

    fun uploadImage(file: File) {
        viewModelScope.launch {
            val compressedFile = file.reduceFileImage()
            val requestImageFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "picture",
                compressedFile.name,
                requestImageFile
            )
            commonRepository.uploadImage(imageMultipart, _fileUploaded)
        }
    }

    fun editOwnedDetailStore(payload: UpdateStoreDetailPayload) {
        viewModelScope.launch {
            repository.addNewOwnedStore(payload, _createNewStoreMessage)
        }
    }
}