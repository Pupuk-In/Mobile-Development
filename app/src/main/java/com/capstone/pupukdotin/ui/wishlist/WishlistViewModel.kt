package com.capstone.pupukdotin.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.items.SearchItemsPayload
import com.capstone.pupukdotin.data.remote.response.wishlist.WishlistResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class WishlistViewModel(private val repository: FertilizerRepository) :  ViewModel() {

    private val _searchWishlistItems =  MutableLiveData<NetworkResult<WishlistResponse>>()
    val searchWishlistItems: LiveData<NetworkResult<WishlistResponse>> get() = _searchWishlistItems

    fun searchWishlistItem(payload: SearchItemsPayload) {
        viewModelScope.launch {
            repository.searchWishlistItem(payload, _searchWishlistItems)
        }
    }
}