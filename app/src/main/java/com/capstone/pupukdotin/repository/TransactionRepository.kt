package com.capstone.pupukdotin.repository

import com.capstone.pupukdotin.data.remote.network.ApiServices

class TransactionRepository(private val apiServices: ApiServices) {

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