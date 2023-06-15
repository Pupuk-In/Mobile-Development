package com.capstone.pupukdotin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.AuthenticationCheckResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    private val _authCheck = MutableLiveData<NetworkResult<AuthenticationCheckResponse>>()
    val authCheck: LiveData<NetworkResult<AuthenticationCheckResponse>>
        get() = _authCheck

    fun getUser(): LiveData<UserModel> = repository.getUser().asLiveData()

    fun checkAuth() {
        viewModelScope.launch {
            repository.authCheck(_authCheck)
        }
    }
}