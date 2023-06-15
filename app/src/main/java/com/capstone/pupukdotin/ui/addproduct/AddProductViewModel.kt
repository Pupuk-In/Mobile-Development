package com.capstone.pupukdotin.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.CreateNewItemPayload
import com.capstone.pupukdotin.data.remote.response.PlantPartResponse
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.capstone.pupukdotin.data.remote.response.store.StoreItemResponse
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.repository.FertilizerRepository
import com.capstone.pupukdotin.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddProductViewModel(
    private val repository: FertilizerRepository,
    private val commonRepository: CommonRepository
) : ViewModel() {

    private val _allTypes = MutableLiveData<NetworkResult<TypeResponse>>()
    val allTypes: LiveData<NetworkResult<TypeResponse>> get() = _allTypes

    private val _allPlants = MutableLiveData<NetworkResult<PlantResponse>>()
    val allPlants: LiveData<NetworkResult<PlantResponse>> get() = _allPlants

    private val _allPlantParts = MutableLiveData<NetworkResult<PlantPartResponse>>()
    val allPlantParts: LiveData<NetworkResult<PlantPartResponse>> get() = _allPlantParts

    private val _fileUploaded = MutableLiveData<NetworkResult<UploadImagesResponse>>()
    val fileUploaded: LiveData<NetworkResult<UploadImagesResponse>> = _fileUploaded

    private val _createProduct = MutableLiveData<NetworkResult<StoreItemResponse>>()
    val createProduct: LiveData<NetworkResult<StoreItemResponse>> get() = _createProduct

    private val mlistImageUploaded = mutableListOf<String>()
    private val _listImageUploaded = MutableLiveData<List<String>>()
    val listImageUploaded: LiveData<List<String>> get() = _listImageUploaded

    fun getAllTypes() {
        viewModelScope.launch {
            repository.getAllTypes(_allTypes)
        }
    }

    fun getAllPlants() {
        viewModelScope.launch {
            repository.getAllPlants(_allPlants)
        }
    }

    fun getAllPlantParts() {
        viewModelScope.launch {
            repository.getAllPlantParts(_allPlantParts)
        }
    }

    fun addImage(value: String) {
        mlistImageUploaded.add(value)
        _listImageUploaded.value = mlistImageUploaded
    }

    fun removeImage(position: Int) {
        if (position < mlistImageUploaded.size) {
            mlistImageUploaded.removeAt(position)
            _listImageUploaded.value = mlistImageUploaded
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

    fun createNewItem(payload: CreateNewItemPayload) {
        viewModelScope.launch {
            repository.createNewItem(payload, _createProduct)
        }
    }
}