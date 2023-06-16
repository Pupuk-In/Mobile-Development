package com.capstone.pupukdotin.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.CreateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.data.remote.response.transaction.CreateTransactionResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.FertilizerRepository
import com.capstone.pupukdotin.repository.TransactionRepository
import kotlinx.coroutines.launch

class CheckOutViewModel(
    private val repository: FertilizerRepository,
    private val authRepository: AuthenticationRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _cartItem = MutableLiveData<NetworkResult<CartItemsResponse>>()
    val cartItem: LiveData<NetworkResult<CartItemsResponse>> = _cartItem

    private val _profileDetail = MutableLiveData<NetworkResult<ProfileDetailResponse>>()
    val profileDetail: LiveData<NetworkResult<ProfileDetailResponse>>
        get() = _profileDetail

    private val _createTransactionResponse =
        MutableLiveData<NetworkResult<CreateTransactionResponse>>()
    val createTransactionResponse: LiveData<NetworkResult<CreateTransactionResponse>> get() = _createTransactionResponse


    fun getCartItems() {
        viewModelScope.launch {
            repository.getCartItems(_cartItem)
        }
    }

    fun createNewTransaction(payload: CreateTransactionPayload) {
        viewModelScope.launch {
            transactionRepository.createNewTransaction(payload, _createTransactionResponse)
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            authRepository.getProfile(_profileDetail)
        }
    }


}