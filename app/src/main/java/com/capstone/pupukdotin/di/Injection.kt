package com.capstone.pupukdotin.di

import android.content.Context
import com.capstone.pupukdotin.data.local.pref.UserPreference
import com.capstone.pupukdotin.data.local.pref.datastore
import com.capstone.pupukdotin.data.remote.network.ApiConfig
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.FertilizerRepository

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
}