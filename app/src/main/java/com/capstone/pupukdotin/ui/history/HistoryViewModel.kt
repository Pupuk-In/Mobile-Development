package com.capstone.pupukdotin.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.repository.TransactionRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _getAllTransactionResponse =
        MutableLiveData<NetworkResult<GetAllTransactionResponse>>()
    val getAllTransactionResponse: LiveData<NetworkResult<GetAllTransactionResponse>> get() = _getAllTransactionResponse

    fun getAllTransaction() {
        viewModelScope.launch {
            repository.getAllTransaction(_getAllTransactionResponse)
        }
    }
}