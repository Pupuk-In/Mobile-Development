package com.capstone.pupukdotin.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.payload.profile.UpdateProfilePayload
import com.capstone.pupukdotin.data.remote.response.UploadImagesResponse
import com.capstone.pupukdotin.data.remote.response.user.ProfileDetailResponse
import com.capstone.pupukdotin.repository.AuthenticationRepository
import com.capstone.pupukdotin.repository.CommonRepository
import com.capstone.pupukdotin.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class LihatProfileViewModel(
    private val repository: AuthenticationRepository,
    private val commonRepository: CommonRepository
) : ViewModel() {

    val profile: LiveData<NetworkResult<ProfileDetailResponse>> = repository.profileDetail

    // YG BENER
    private val _editProfile = MutableLiveData<NetworkResult<ProfileDetailResponse>>()
    val editProfile: LiveData<NetworkResult<ProfileDetailResponse>> get() = _editProfile

    private val _fileUploaded = MutableLiveData<NetworkResult<UploadImagesResponse>>()
    val fileUploaded: LiveData<NetworkResult<UploadImagesResponse>> = _fileUploaded

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile()
        }
    }

    fun editProfile(payload: UpdateProfilePayload) {
        viewModelScope.launch {
            repository.editProfile(payload, _editProfile)
        }
    }

    fun uploadImage(file: File) {
        viewModelScope.launch {
            val compressedFile = file.reduceFileImage()
            val requestImageFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "picture",
                compressedFile.name,
                requestImageFile
            )
            commonRepository.uploadImage(imageMultipart, _fileUploaded)
        }
    }
}