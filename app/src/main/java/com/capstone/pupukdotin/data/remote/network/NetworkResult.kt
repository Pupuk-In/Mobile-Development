package com.capstone.pupukdotin.data.remote.network

sealed class NetworkResult<out R> private constructor() {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: Any) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}
