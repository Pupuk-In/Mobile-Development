package com.capstone.pupukdotin.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.BasicResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    val profile: LiveData<NetworkResult<ProfileDetailResponse>> = repository.profileDetail
    val logout: LiveData<NetworkResult<BasicResponse>> = repository.logout

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            repository.logoutUser()
        }
    }

}