package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.UpdateStoreDetailPayload
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.repository.StoreRepository
import com.capstone.pupukdotin.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditStoreViewModel(
    private val repository: StoreRepository,
    private val commonRepository: CommonRepository
) : ViewModel() {

    private val _fileUploaded = MutableLiveData<NetworkResult<UploadImagesResponse>>()
    val fileUploaded: LiveData<NetworkResult<UploadImagesResponse>> = _fileUploaded

    private val _ownedDetailStore = MutableLiveData<NetworkResult<OwnedStoreDetailResponse>>()
    val ownedDetailStore: LiveData<NetworkResult<OwnedStoreDetailResponse>> = _ownedDetailStore

    private val _editStoreMessage = MutableLiveData<NetworkResult<String>>()
    val editStoreMessage: LiveData<NetworkResult<String>> get() = _editStoreMessage

    fun getOwnedDetailStore() {
        viewModelScope.launch {
            repository.getOwnedDetailStore(_ownedDetailStore)
        }
    }

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
            repository.editOwnedDetailStore(payload, _editStoreMessage)
        }
    }
}