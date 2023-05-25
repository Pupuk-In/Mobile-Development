package com.capstone.pupukdotin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.data.local.pref.UserPreference
import com.capstone.pupukdotin.data.remote.network.ApiServices
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val apiServices: ApiServices,
    private val userPreference: UserPreference
) {

    private val _login = MutableLiveData<NetworkResult<LoginResponse>>()
    val login: LiveData<NetworkResult<LoginResponse>>
        get() = _login

    private val _register = MutableLiveData<NetworkResult<RegisterResponse>>()
    val register: LiveData<NetworkResult<RegisterResponse>>
        get() = _register

    suspend fun login(payload: LoginPayload) {
        _login.value = NetworkResult.Loading
        try {
            val result = apiServices.authLogin(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _login.value = NetworkResult.Success(responseBody)
                }
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _login.value = NetworkResult.Error(mapper.message)
                    Log.d("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _login.value = NetworkResult.Error(result.message())
                    Log.d("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _login.value = NetworkResult.Error(e.message.toString())
            Log.d("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun register(payload: RegisterPayload) {
        _register.value = NetworkResult.Loading
        try {
            val result = apiServices.authRegister(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _register.value = NetworkResult.Success(responseBody)
                }
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), RegisterResponse::class.java)
                    _register.value = NetworkResult.Error(mapper)
                    Log.e("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _register.value = NetworkResult.Error(result.message())
                    Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _register.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

    suspend fun saveUser(userModel: UserModel) {
        userPreference.saveUser(userModel)
    }

    fun getUser(): Flow<UserModel> = userPreference.getUser()


    companion object {
        @Volatile
        private var instance: AuthenticationRepository? = null

        fun getInstance(
            apiServices: ApiServices,
            userPreference: UserPreference
        ): AuthenticationRepository =
            instance ?: synchronized(this) {
                AuthenticationRepository(apiServices, userPreference).apply {
                    instance = this
                }
            }
    }

}