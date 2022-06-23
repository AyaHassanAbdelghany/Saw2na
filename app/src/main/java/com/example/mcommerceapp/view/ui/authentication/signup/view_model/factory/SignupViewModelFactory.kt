package com.example.mcommerceapp.view.ui.authentication.signup.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signup.view_model.SignupViewModel

class SignupViewModelFactory(private var userRepo: UserRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            SignupViewModel(userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}