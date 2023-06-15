package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.store.StoreAllItemsResponse
import com.capstone.pupukdotin.repository.StoreRepository
import kotlinx.coroutines.launch

class StoreProdukViewModel(private val repository: StoreRepository) : ViewModel() {

    private val _allItems = MutableLiveData<NetworkResult<StoreAllItemsResponse>>()
    val allItems: LiveData<NetworkResult<StoreAllItemsResponse>> = _allItems



    fun getAllItems() {
        viewModelScope.launch {
            repository.getAllItems(_allItems)
        }
    }
}