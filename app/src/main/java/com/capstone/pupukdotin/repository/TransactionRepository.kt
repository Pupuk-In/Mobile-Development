package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.CreateTransactionPayload
import com.capstone.pupukdotin.data.remote.payload.transaction.UpdateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.store.GetAllTransactionStoreResponse
import com.capstone.pupukdotin.data.remote.response.transaction.CreateTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.UpdateTransactionResponse

class TransactionRepository(private val apiServices: ApiServices) {

    suspend fun createNewTransaction(
        payload: CreateTransactionPayload,
        _createTransactionResponse: MutableLiveData<NetworkResult<CreateTransactionResponse>>
    ) {
        _createTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.createNewTransaction(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _createTransactionResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _createTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _createTransactionResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getAllTransaction(_getAllTransactionResponse: MutableLiveData<NetworkResult<GetAllTransactionResponse>>) {
        _getAllTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.getAllTransaction()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _getAllTransactionResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _getAllTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getAllTransactionResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getDetailTransaction(
        idTransaction: Int,
        _getTransactionResponse: MutableLiveData<NetworkResult<GetDetailTransactionResponse>>
    ) {
        _getTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.getDetailTransaction(idTransaction)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _getTransactionResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _getTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getTransactionResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getAllTransactionStore(_getAllTransactionStoreResponse: MutableLiveData<NetworkResult<GetAllTransactionStoreResponse>>) {
        _getAllTransactionStoreResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.getAllTransactionStore()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _getAllTransactionStoreResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _getAllTransactionStoreResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getAllTransactionStoreResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getDetailTransactionStore(
        idTransaction: Int,
        _getTransactionStoreResponse: MutableLiveData<NetworkResult<GetDetailTransactionResponse>>
    ) {
        _getTransactionStoreResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.getDetailTransactionStore(idTransaction)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _getTransactionStoreResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _getTransactionStoreResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getTransactionStoreResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun updateStatusTransactionStore(
        payload: UpdateTransactionPayload,
        idTransaction: Int,
        _updateStatusTransactionResponse: MutableLiveData<NetworkResult<UpdateTransactionResponse>>
    ) {
        _updateStatusTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.updateStatusTransactionStore(payload, idTransaction)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _updateStatusTransactionResponse.value =
                    NetworkResult.Success(responseBody)
            } else {
                _updateStatusTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _updateStatusTransactionResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    companion object {
        @Volatile
        private var instance: TransactionRepository? = null

        fun getInstance(
            apiServices: ApiServices
        ): TransactionRepository =
            instance ?: synchronized(this) {
                TransactionRepository(apiServices).apply {
                    instance = this
                }
            }
    }
}