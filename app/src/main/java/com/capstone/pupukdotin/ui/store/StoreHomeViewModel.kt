package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.repository.StoreRepository
import kotlinx.coroutines.launch

class StoreHomeViewModel(private val repository: StoreRepository): ViewModel() {

    private val _ownedDetailStore = MutableLiveData<NetworkResult<OwnedStoreDetailResponse>>()
    val ownedDetailStore: LiveData<NetworkResult<OwnedStoreDetailResponse>> = _ownedDetailStore

    fun getOwnedDetailStore() {
        viewModelScope.launch {
            repository.getOwnedDetailStore(_ownedDetailStore)
        }
    }
}