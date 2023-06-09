package com.capstone.pupukdotin.di

import android.content.Context
import com.capstone.pupukdotin.data.local.pref.UserPreference
import com.capstone.pupukdotin.data.local.pref.datastore
import com.capstone.pupukdotin.data.remote.network.ApiConfig
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.repository.FertilizerRepository
import com.capstone.pupukdotin.repository.MachineLearningRepository
import com.capstone.pupukdotin.repository.StoreRepository
import com.capstone.pupukdotin.repository.TransactionRepository

object Injection {

    fun getAuthRepo(context: Context): AuthenticationRepository {
        val services = ApiConfig.getApiService()
        val preference = UserPreference.getInstance(context.datastore)

        return AuthenticationRepository.getInstance(services, preference)
    }

    fun getFertilizerRepo(): FertilizerRepository {
        val services = ApiConfig.getApiService()
        return FertilizerRepository.getInstance(services)
    }

    fun getStoreRepo(): StoreRepository {
        val services = ApiConfig.getApiService()
        return StoreRepository.getInstance(services)
    }

    fun getCommonRepo(): CommonRepository {
        val services = ApiConfig.getCommonServices()
        return CommonRepository.getInstance(services)
    }

    fun getTransactionRepo(): TransactionRepository {
        val services = ApiConfig.getApiService()
        return TransactionRepository.getInstance(services)
    }

    fun getMLRepo(): MachineLearningRepository {
        val services = ApiConfig.getTfService()
        return MachineLearningRepository.getInstance(services)
    }
}