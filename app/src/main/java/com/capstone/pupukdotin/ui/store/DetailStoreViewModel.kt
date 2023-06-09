package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.store.SearchCatalogPayload
import com.capstone.pupukdotin.data.remote.response.store.StoreDetailResponse
import com.capstone.pupukdotin.repository.StoreRepository
import kotlinx.coroutines.launch

class DetailStoreViewModel(private val repository: StoreRepository) : ViewModel() {

    val detailStore: LiveData<NetworkResult<StoreDetailResponse>> = repository.detailStore

    fun getDetailStore(payload: SearchCatalogPayload, idStore: Int) {
        viewModelScope.launch {
            repository.getDetailStore(payload, idStore)
        }
    }
}