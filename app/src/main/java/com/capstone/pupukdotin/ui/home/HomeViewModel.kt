package com.capstone.pupukdotin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.FertilizerPlantResponse
import com.capstone.pupukdotin.data.remote.response.FertilizerTypeResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: FertilizerRepository): ViewModel() {

    val types: LiveData<NetworkResult<FertilizerTypeResponse>> = repository.types
    val plants: LiveData<NetworkResult<FertilizerPlantResponse>> = repository.plants

    fun getTypes() {
        viewModelScope.launch {
            repository.getTypes()
        }
    }

    fun getPlants() {
        viewModelScope.launch {
            repository.getPlants()
        }
    }
}