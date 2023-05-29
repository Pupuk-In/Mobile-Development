package com.capstone.pupukdotin.di

import android.content.Context
import com.capstone.pupukdotin.data.local.pref.UserPreference
import com.capstone.pupukdotin.data.local.pref.datastore
import com.capstone.pupukdotin.data.remote.network.ApiConfig
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.PupukItemRepository

object Injection {

    fun getAuthRepo(context: Context): AuthenticationRepository {
        val services = ApiConfig.getApiService()
        val preference = UserPreference.getInstance(context.datastore)

        return AuthenticationRepository.getInstance(services, preference)
    }

    fun getPupukItemRepo(): PupukItemRepository {
        val services = ApiConfig.getApiService()
        return PupukItemRepository.getInstance(services)
    }
}