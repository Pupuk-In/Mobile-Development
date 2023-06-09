package com.capstone.pupukdotin.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.store.OwnedStoreDetailResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.StoreRepository
import kotlinx.coroutines.launch

class StoreProfileViewModel(private val repository: StoreRepository, private val authRepository: AuthenticationRepository) : ViewModel() {

    private val _getProfile = MutableLiveData<NetworkResult<OwnedStoreDetailResponse>>()
    val getprofile: LiveData<NetworkResult<OwnedStoreDetailResponse>> get() = _getProfile

    fun getProfile() {
        viewModelScope.launch {
            repository.getOwnedDetailStore(_getProfile)
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            authRepository.logoutUser()
        }
    }
}