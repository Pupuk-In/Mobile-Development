package com.capstone.pupukdotin.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddProductViewModel: ViewModel() {

    private val mlistImageUploaded = mutableListOf<String>()
    private val _listImageUploaded = MutableLiveData<List<String>>()
    val listImageUploaded: LiveData<List<String>> get() = _listImageUploaded


    fun addImage(value: String) {
        mlistImageUploaded.add(value)
        _listImageUploaded.value = mlistImageUploaded
    }

    fun removeImage(position: Int) {
        if(position < mlistImageUploaded.size) {
            mlistImageUploaded.removeAt(position)
            _listImageUploaded.value = mlistImageUploaded
        }
    }
}