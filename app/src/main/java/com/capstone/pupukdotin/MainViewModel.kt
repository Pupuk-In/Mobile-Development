package com.capstone.pupukdotin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.pupukdotin.data.local.pref.UserModel
import com.capstone.pupukdotin.repository.AuthenticationRepository

class MainViewModel(private val repository: AuthenticationRepository) : ViewModel() {
    fun getUser(): LiveData<UserModel> = repository.getUser().asLiveData()
}