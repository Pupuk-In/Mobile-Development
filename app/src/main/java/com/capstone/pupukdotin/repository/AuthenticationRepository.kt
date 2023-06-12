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
import com.capstone.pupukdotin.data.remote.payload.profile.UpdateProfilePayload
import com.capstone.pupukdotin.data.remote.response.AuthenticationCheckResponse
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class AuthenticationRepository(
    private val apiServices: ApiServices,
    private val userPreference: UserPreference
) {


    private val _authCheck = MutableLiveData<NetworkResult<AuthenticationCheckResponse>>()
    val authCheck: LiveData<NetworkResult<AuthenticationCheckResponse>>
        get() = _authCheck

    private val _profileDetail = MutableLiveData<NetworkResult<ProfileDetailResponse>>()
    val profileDetail: LiveData<NetworkResult<ProfileDetailResponse>>
        get() = _profileDetail


    private val _logout = MutableLiveData<NetworkResult<BasicResponse>>()
    val logout: LiveData<NetworkResult<BasicResponse>>
        get() = _logout

    suspend fun login(payload: LoginPayload, _login: MutableLiveData<NetworkResult<LoginResponse>>) {
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

    suspend fun register(payload: RegisterPayload, _register: MutableLiveData<NetworkResult<RegisterResponse>>) {
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
                    _register.value = NetworkResult.Error(mapper.message)
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

    suspend fun authCheck() {
        _authCheck.value = NetworkResult.Loading
        try {
            val result = apiServices.authCheck()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _authCheck.value = NetworkResult.Success(responseBody)
                }
            } else {
                _authCheck.value = NetworkResult.Error(result.message())
                Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
            }
        } catch (e: Exception) {
            _authCheck.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }


    suspend fun getProfile() {
        _profileDetail.value = NetworkResult.Loading
        try {
            val result = apiServices.getDetailProfile()
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _profileDetail.value = NetworkResult.Success(responseBody)
                }
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _profileDetail.value = NetworkResult.Error(mapper.message)
                    Log.e("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _profileDetail.value = NetworkResult.Error(result.message())
                    Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _profileDetail.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }


    suspend fun editProfile(payload: UpdateProfilePayload, _editProfile:MutableLiveData<NetworkResult<ProfileDetailResponse>>) {
        _editProfile.value = NetworkResult.Loading
        try {
            val result = apiServices.updateDetailProfile(payload)
            if (result.isSuccessful) {
                val responseBody = result.body()
                if (responseBody != null) {
                    _editProfile.value = NetworkResult.Success(responseBody)
                }
            } else {
                val responseBody = result.errorBody()
                if (responseBody != null) {
                    val mapper =
                        Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                    _editProfile.value = NetworkResult.Error(mapper.message)
                    Log.e("ini_log_message", "onFailure: ${mapper.message}")
                } else {
                    _editProfile.value = NetworkResult.Error(result.message())
                    Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
                }
            }
        } catch (e: Exception) {
            _editProfile.value = NetworkResult.Error(e.message.toString())
            Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
        }
    }

        suspend fun logout() {
            _logout.value = NetworkResult.Loading
            try {
                val result = apiServices.authLogout()
                if (result.isSuccessful) {
                    val responseBody = result.body()
                    if (responseBody != null) {
                        _logout.value = NetworkResult.Success(responseBody)
                    }
                } else {
                    val responseBody = result.errorBody()
                    if (responseBody != null) {
                        val mapper =
                            Gson().fromJson(responseBody.string(), BasicResponse::class.java)
                        _logout.value = NetworkResult.Error(mapper.message)
                        Log.e("ini_log_message", "onFailure: ${mapper.message}")
                    } else {
                        _logout.value = NetworkResult.Error(result.message())
                        Log.e("ini_log_nullMessage", "onFailure: ${result.message()}")
                    }
                }
            } catch (e: Exception) {
                _logout.value = NetworkResult.Error(e.message.toString())
                Log.e("ini_log_exception", "onFailure: ${e.message.toString()}")
            }
        }

        suspend fun logoutUser() {
            userPreference.logout()
        }


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