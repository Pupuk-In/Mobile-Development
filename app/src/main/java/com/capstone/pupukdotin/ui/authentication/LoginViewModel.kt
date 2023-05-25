package com.capstone.pupukdotin.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.LoginPayload
import com.capstone.pupukdotin.data.remote.response.LoginResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    val login: LiveData<NetworkResult<LoginResponse>> = repository.login

    fun login(payload: LoginPayload) {
        viewModelScope.launch {
            repository.login(payload)
        }
    }

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveUser(userModel)
        }
    }
}