package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.store.GetAllTransactionStoreResponse
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.repository.TransactionRepository
import kotlinx.coroutines.launch

class StorePesananViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _getAllTransactionStoreResponse = MutableLiveData<NetworkResult<GetAllTransactionStoreResponse>>()
    val getAllTransactionStoreResponse: LiveData<NetworkResult<GetAllTransactionStoreResponse>> get() = _getAllTransactionStoreResponse

    fun getAllTransactionStore() {
        viewModelScope.launch {
            repository.getAllTransactionStore(_getAllTransactionStoreResponse)
        }
    }
}