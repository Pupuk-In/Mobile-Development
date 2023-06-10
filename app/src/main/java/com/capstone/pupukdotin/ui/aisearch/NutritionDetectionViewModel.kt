package com.capstone.pupukdotin.ui.aisearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class NutritionDetectionViewModel(private val repository: FertilizerRepository, private val commonRepository: CommonRepository): ViewModel() {

    private val _imageUploaded = MutableLiveData("")
    val imageUploaded: LiveData<String> = _imageUploaded

    private val _allPlants =  MutableLiveData<NetworkResult<PlantResponse>>()
    val allPlants: LiveData<NetworkResult<PlantResponse>> get() = _allPlants

    fun putUrl(value: String) {
        _imageUploaded.value = value
    }

    fun removeUrl() {
        _imageUploaded.value = ""
    }

    fun uploadImage() {

    }

    fun getAllPlants() {
        viewModelScope.launch {
            repository.getAllPlants(_allPlants)
        }
    }

}