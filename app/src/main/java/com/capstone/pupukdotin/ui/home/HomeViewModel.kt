package com.capstone.pupukdotin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.PlantResponse
import com.capstone.pupukdotin.data.remote.response.TypeResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: FertilizerRepository): ViewModel() {

    val types: LiveData<NetworkResult<TypeResponse>> = repository.types
    val plants: LiveData<NetworkResult<PlantResponse>> = repository.plants

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