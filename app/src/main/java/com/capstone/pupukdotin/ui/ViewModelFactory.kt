package com.capstone.pupukdotin.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pupukdotin.MainViewModel
import com.capstone.pupukdotin.di.Injection
import com.capstone.pupukdotin.ui.aisearch.NutritionDetectionViewModel
import com.capstone.pupukdotin.ui.authentication.LoginViewModel
import com.capstone.pupukdotin.ui.authentication.RegisterViewModel
import com.capstone.pupukdotin.ui.cart.CartViewModel
import com.capstone.pupukdotin.ui.detail.DetailItemViewModel
import com.capstone.pupukdotin.ui.home.HomeViewModel
import com.capstone.pupukdotin.ui.profile.LihatProfileViewModel
import com.capstone.pupukdotin.ui.profile.ProfileViewModel
import com.capstone.pupukdotin.ui.search.SearchResultViewModel
import com.capstone.pupukdotin.ui.store.DetailStoreViewModel
import com.capstone.pupukdotin.ui.store.EditStoreViewModel
import com.capstone.pupukdotin.ui.store.StoreHomeViewModel
import com.capstone.pupukdotin.ui.store.StoreProfileViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(Injection.getAuthRepo(context)) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(Injection.getAuthRepo(context)) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Injection.getAuthRepo(context)) as T
        } else if (modelClass.isAssignableFrom(DetailItemViewModel::class.java)) {
            return DetailItemViewModel(Injection.getFertilizerRepo()) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(Injection.getFertilizerRepo()) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(Injection.getAuthRepo(context)) as T
        } else if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            return SearchResultViewModel(Injection.getFertilizerRepo()) as T
        } else if (modelClass.isAssignableFrom(DetailStoreViewModel::class.java)) {
            return DetailStoreViewModel(Injection.getStoreRepo()) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(Injection.getFertilizerRepo()) as T
        } else if (modelClass.isAssignableFrom(LihatProfileViewModel::class.java)) {
            return LihatProfileViewModel(Injection.getAuthRepo(context), Injection.getCommonRepo()) as T
        } else if (modelClass.isAssignableFrom(NutritionDetectionViewModel::class.java)) {
            return NutritionDetectionViewModel(Injection.getFertilizerRepo(), Injection.getCommonRepo()) as T
        }  else if (modelClass.isAssignableFrom(StoreHomeViewModel::class.java)) {
            return StoreHomeViewModel(Injection.getStoreRepo()) as T
        } else if (modelClass.isAssignableFrom(EditStoreViewModel::class.java)) {
            return EditStoreViewModel(Injection.getStoreRepo(), Injection.getCommonRepo()) as T
        } else if (modelClass.isAssignableFrom(StoreProfileViewModel::class.java)) {
            return StoreProfileViewModel(Injection.getStoreRepo()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}