package com.capstone.pupukdotin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.items.DetailItemResponse
import com.capstone.pupukdotin.repository.FertilizerRepository
import kotlinx.coroutines.launch

class DetailItemViewModel(private val repository: FertilizerRepository) : ViewModel() {

    private val _itemCount = MutableLiveData<Int>().apply {
        value = 0
    }
    val itemCount: LiveData<Int> = _itemCount

    val detailItem: LiveData<NetworkResult<DetailItemResponse>> = repository.detailItem

    fun getDetailItem(id: Int) {
        viewModelScope.launch {
            repository.getDetailItem(id)
        }
    }

    fun setItemCount(value: Int, maxNum: Int) {
        if(value <= maxNum) {
            _itemCount.value?.let { _itemCount.value = value }
        } else {
            _itemCount.value?.let { _itemCount.value = maxNum }
        }
    }

    fun addItemCount(maxNum: Int) {
        _itemCount.value?.let { num ->
            if (num < maxNum) _itemCount.value = num + 1
        }
    }

    fun subtractItemCount() {
        _itemCount.value?.let { num ->
            if (num != 0) _itemCount.value = num - 1
        }
    }
}