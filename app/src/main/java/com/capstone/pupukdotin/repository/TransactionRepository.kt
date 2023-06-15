package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.transaction.CreateTransactionPayload
import com.capstone.pupukdotin.data.remote.response.transaction.CreateTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.GetAllTransactionResponse
import com.capstone.pupukdotin.data.remote.response.transaction.GetDetailTransactionResponse

class TransactionRepository(private val apiServices: ApiServices) {

    suspend fun createNewTransaction(payload: CreateTransactionPayload, _createTransactionResponse: MutableLiveData<NetworkResult<CreateTransactionResponse>>) {
        _createTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.createNewTransaction(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _createTransactionResponse.value = NetworkResult.Success(responseBody)
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
                if (responseBody != null) _getAllTransactionResponse.value = NetworkResult.Success(responseBody)
            } else {
                _getAllTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getAllTransactionResponse.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun getDetailTransaction(idTransaction: Int, _getTransactionResponse: MutableLiveData<NetworkResult<GetDetailTransactionResponse>>) {
        _getTransactionResponse.value = NetworkResult.Loading
        try {
            val result = apiServices.getDetailTransaction(idTransaction)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) _getTransactionResponse.value = NetworkResult.Success(responseBody)
            } else {
                _getTransactionResponse.value = NetworkResult.Error(result.message())
                Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _getTransactionResponse.value = NetworkResult.Error(e.message.toString())
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