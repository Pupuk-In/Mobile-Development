package com.capstone.pupukdotin.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.items.SearchItemsResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repository: FertilizerRepository): ViewModel() {
    val searchItem: LiveData<NetworkResult<SearchItemsResponse>> = repository.searchItem

    fun searchItem(payload: SearchItemsPayload) {
        viewModelScope.launch {
            repository.searchItem(payload)
        }
    }
}