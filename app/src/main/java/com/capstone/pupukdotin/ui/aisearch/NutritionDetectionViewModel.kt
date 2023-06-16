package com.capstone.pupukdotin.ui.aisearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.ai.NutritionDetectionResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import com.capstone.pupukdotin.repository.MachineLearningRepository
import com.capstone.pupukdotin.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class NutritionDetectionViewModel(
    private val repository: FertilizerRepository,
    private val mlRepository: MachineLearningRepository
) : ViewModel() {

    private val _imageUploaded = MutableLiveData<File>()
    val imageUploaded: LiveData<File> = _imageUploaded

    private val _detectionResult = MutableLiveData<NetworkResult<NutritionDetectionResponse>>()
    val detectionResult: LiveData<NetworkResult<NutritionDetectionResponse>> get() = _detectionResult

    private val _allPlants = MutableLiveData<NetworkResult<PlantResponse>>()
    val allPlants: LiveData<NetworkResult<PlantResponse>> get() = _allPlants

    fun putFile(value: File) {
        _imageUploaded.value = value
    }

    fun removeFile() {
        _imageUploaded.value = File("")
    }

    fun getAllPlants() {
        viewModelScope.launch {
            repository.getAllPlants(_allPlants)
        }
    }

    fun startDetection() {
        viewModelScope.launch {
            val file = _imageUploaded.value
            val compressedFile = file?.reduceFileImage()
            val requestImageFile =
                compressedFile?.asRequestBody("image/*".toMediaTypeOrNull()) as RequestBody
            val imageMultipart = MultipartBody.Part.createFormData(
                "file",
                compressedFile.name,
                requestImageFile
            )

            mlRepository.detectNutritionImage(imageMultipart, _detectionResult)
        }
    }

}