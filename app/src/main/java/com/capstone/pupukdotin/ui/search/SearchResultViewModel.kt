package com.capstone.pupukdotin.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.SearchResultResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class SearchResultViewModel(private val repository: FertilizerRepository): ViewModel() {
    val searchItem: LiveData<NetworkResult<SearchResultResponse>> = repository.searchItem

    fun searchItem(name: String) {
        viewModelScope.launch {
            repository.searchItem(name)
        }
    }
}