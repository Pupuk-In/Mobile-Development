package com.capstone.pupukdotin.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.RegisterPayload
import com.capstone.pupukdotin.data.remote.response.RegisterResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthenticationRepository) : ViewModel() {
    val register: LiveData<NetworkResult<RegisterResponse>> = repository.register

    fun register(payload: RegisterPayload) {
        viewModelScope.launch {
            repository.register(payload)
        }
    }
}