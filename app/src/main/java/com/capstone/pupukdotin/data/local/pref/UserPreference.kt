package com.capstone.pupukdotin.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.pupukdotin.data.remote.network.ApiConfig
import com.capstone.pupukdotin.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[AUTH_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[AUTH_KEY] = user.tokenAuth
            preferences[STATE_KEY] = user.isLogin
        }
        setToken(user.tokenAuth)
    }

    suspend fun logout() {
        dataStore.edit { pref ->
            pref[AUTH_KEY] = ""
            pref[STATE_KEY]= false
        }
        ApiConfig.removeToken()
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val AUTH_KEY = stringPreferencesKey(Constant.KEY_AUTH_PREFERENCES)
        private val STATE_KEY = booleanPreferencesKey(Constant.KEY_STATE_PREFERENCES)

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }

        fun setToken(token: String) {
            ApiConfig.setToken(token)
        }
    }
}