package com.capstone.pupukdotin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.carts.AddEditCartPayload
import com.capstone.pupukdotin.data.remote.payload.wishlist.AddWishlistPayload
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.data.remote.response.wishlist.AddWishlistItemResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class DetailItemViewModel(private val repository: FertilizerRepository) : ViewModel() {

    private val _itemCount = MutableLiveData<Int>().apply {
        value = 0
    }
    val itemCount: LiveData<Int> = _itemCount

    private val _detailItem = MutableLiveData<NetworkResult<DetailItemResponse>>()
    val detailItem: LiveData<NetworkResult<DetailItemResponse>> = _detailItem

    private val _createWishlistItem = MutableLiveData<NetworkResult<AddWishlistItemResponse>>()
    val createWishlistItem:LiveData<NetworkResult<AddWishlistItemResponse>> get() = _createWishlistItem

    private val _deleteItemMessage = MutableLiveData<NetworkResult<BasicResponse>>()
    val deleteItemMessage:LiveData<NetworkResult<BasicResponse>> get() = _deleteItemMessage

    private val _addCartMessage = MutableLiveData<NetworkResult<String>>()
    val addCartMessage: LiveData<NetworkResult<String>> get()= _addCartMessage

    fun getDetailItem(id: Int) {
        viewModelScope.launch {
            repository.getDetailItem(id, _detailItem)
        }
    }

    fun addItemToCart(idItem: Int) {
        if(_itemCount.value != 0 || _itemCount.value != null) {
            viewModelScope.launch {
                repository.addCartItems(AddEditCartPayload(quantity = _itemCount.value ?:1, itemId = idItem), _addCartMessage)
            }
        }
    }

    fun createWishlistItem(payload: AddWishlistPayload) {
        viewModelScope.launch {
            repository.addWishlistItem(payload, _createWishlistItem)
        }
    }

    fun deleteWishlistItem(idItem: Int) {
        viewModelScope.launch {
            repository.deleteWishlistItem(idItem, _deleteItemMessage)
        }
    }

    fun setItemCount(value: Int, maxNum: Int) {
        if(value <= maxNum) {
            _itemCount.value?.let { _itemCount.value = value }
        } else {
            _itemCount.value?.let { _itemCount.value = maxNum }
        }
    }

    fun addItemCount(maxNum: Int) {
        _itemCount.value?.let { num ->
            if (num < maxNum) _itemCount.value = num + 1
        }
    }

    fun subtractItemCount() {
        _itemCount.value?.let { num ->
            if (num != 0) _itemCount.value = num - 1
        }
    }
}