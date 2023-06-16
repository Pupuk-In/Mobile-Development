package com.capstone.pupukdotin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.repository.TransactionRepository
import kotlinx.coroutines.launch

class DetailOrderViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _getTransactionResponse =
        MutableLiveData<NetworkResult<GetDetailTransactionResponse>>()
    val getTransactionResponse: LiveData<NetworkResult<GetDetailTransactionResponse>> get() = _getTransactionResponse

    fun getDetailTransaction(idTransaction: Int) {
        viewModelScope.launch {
            repository.getDetailTransaction(idTransaction, _getTransactionResponse)
        }
    }

}