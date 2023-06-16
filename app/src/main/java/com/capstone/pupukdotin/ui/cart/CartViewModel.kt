package com.capstone.pupukdotin.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.carts.AddEditCartPayload
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: FertilizerRepository) : ViewModel() {

    private val mlistItem = mutableListOf<CartItemsResponse.CartItem>()

    private val _listItem = MutableLiveData<List<CartItemsResponse.CartItem>>()
    val listItem: LiveData<List<CartItemsResponse.CartItem>> get() = _listItem

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _cartItem = MutableLiveData<NetworkResult<CartItemsResponse>>()
    val cartItem: LiveData<NetworkResult<CartItemsResponse>> = _cartItem

    private val _editCartMessage = MutableLiveData<NetworkResult<String>>()
    val editCartMessage: LiveData<NetworkResult<String>> get() = _editCartMessage

    private val _deleteCartMessage = MutableLiveData<NetworkResult<BasicResponse>>()
    val deleteCartMessage: LiveData<NetworkResult<BasicResponse>> get() = _deleteCartMessage

    private val _positionItemSelected = MutableLiveData(-1)

    fun getCartItems() {
        reset()
        viewModelScope.launch {
            repository.getCartItems(_cartItem)
        }
    }


    private fun editCartItems(payload: AddEditCartPayload, idItem: Int) {
        viewModelScope.launch {
            repository.editCartItems(payload, idItem, _editCartMessage)
        }
    }

    fun deleteCartItemFromServer(position: Int) {
        _positionItemSelected.value = position
        viewModelScope.launch {
            repository.deleteCartItems(mlistItem[position].id ?: 0, _deleteCartMessage)
        }
    }

    fun editCart() {
        mlistItem.forEach { item ->
            editCartItems(payload = AddEditCartPayload(item.quantity ?: 0), item.id ?: 0)
        }
    }

    private fun reset() {
        mlistItem.clear()
        _listItem.value = mlistItem
        _totalPrice.value = 0
    }

    fun addAll(listData: List<CartItemsResponse.CartItem>) {
        mlistItem.clear()
        mlistItem.addAll(listData)
        _listItem.value = mlistItem
        setTotalPrice()
    }

    fun removeItem() {
        val currentPosition = _positionItemSelected.value ?:-1
        if(currentPosition < mlistItem.size && currentPosition > 0) {
            mlistItem.removeAt(currentPosition)
            setTotalPrice()
            _positionItemSelected.value = -1
        }
    }

    fun addQuantity(position: Int) {
        var prevQuantity = mlistItem[position].quantity ?: 0
        val itemPrice = mlistItem[position].item?.price ?: 0

        if (prevQuantity >= 0) {
            mlistItem[position].quantity = (prevQuantity + 1)
            mlistItem[position].price = (prevQuantity + 1) * itemPrice

            _listItem.value = mlistItem
            setTotalPrice()
        }
        else{
            // Do nothing
        }

    }

    fun subtractQuantity(position: Int) {
        val prevQuantity = mlistItem[position].quantity ?: 0
        val itemPrice = mlistItem[position].item?.price ?: 0

        if (prevQuantity > 0) {
            mlistItem[position].quantity = (prevQuantity - 1)
            mlistItem[position].price = (prevQuantity - 1) * itemPrice

            _listItem.value = mlistItem
            setTotalPrice()
        }
    }

    private fun setTotalPrice() {
        val total = mlistItem.sumOf { it.price ?: 0 }
        _totalPrice.value = total
    }
}