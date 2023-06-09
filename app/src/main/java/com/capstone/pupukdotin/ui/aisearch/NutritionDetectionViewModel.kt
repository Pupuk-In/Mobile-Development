package com.capstone.pupukdotin.ui.aisearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NutritionDetectionViewModel: ViewModel() {

    private val _imageUploaded = MutableLiveData("")
    val imageUploaded: LiveData<String> = _imageUploaded

    fun putUrl(value: String) {
        _imageUploaded.value = value
    }

    fun removeUrl() {
        _imageUploaded.value = ""
    }

}