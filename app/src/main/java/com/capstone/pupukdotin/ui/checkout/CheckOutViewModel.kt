package com.capstone.pupukdotin.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class CheckOutViewModel(private val repository: FertilizerRepository) : ViewModel() {

    private val _cartItem = MutableLiveData<NetworkResult<CartItemsResponse>>()
    val cartItem: LiveData<NetworkResult<CartItemsResponse>> = _cartItem

    fun getCartItems() {
        viewModelScope.launch {
            repository.getCartItems(_cartItem)
        }
    }
}