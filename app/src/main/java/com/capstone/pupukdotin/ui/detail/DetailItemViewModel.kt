package com.capstone.pupukdotin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.DetailItemResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class DetailItemViewModel(private val repository: FertilizerRepository): ViewModel(){
    val detailItem: LiveData<NetworkResult<DetailItemResponse>> = repository.detailItem

    fun getDetailItem(id: Int) {
        viewModelScope.launch {
            repository.getDetailItem(id)
        }
    }
}