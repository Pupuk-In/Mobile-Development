package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.UpdateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.UpdateTransactionResponse
import com.capstone.pupukdotin.repository.TransactionRepository
import kotlinx.coroutines.launch

class StoreDetailOrderViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _getTransactionStoreResponse = MutableLiveData<NetworkResult<GetDetailTransactionResponse>>()
    val getTransactionStoreResponse: LiveData<NetworkResult<GetDetailTransactionResponse>> get() = _getTransactionStoreResponse

    private val _updateStatusTransactionResponse = MutableLiveData<NetworkResult<UpdateTransactionResponse>>()
    val updateStatusTransactionResponse: LiveData<NetworkResult<UpdateTransactionResponse>> get() = _updateStatusTransactionResponse


    fun getDetailTransactionStore(idTransaction: Int) {
        viewModelScope.launch {
            repository.getDetailTransactionStore(idTransaction, _getTransactionStoreResponse)
        }
    }

    fun updateStatusTransactionStore(payload: UpdateTransactionPayload, idTransaction: Int) {
        viewModelScope.launch {
            repository.updateStatusTransactionStore(payload, idTransaction, _updateStatusTransactionResponse)
        }
    }

}