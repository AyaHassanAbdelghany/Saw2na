package com.example.mcommerceapp.view.ui.profile.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.view.ui.profile.view_model.ProfileViewModel

class ProfileViewModelFactory(private var userRepo: UserRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}