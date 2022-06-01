package com.example.mcommerceapp.view.ui.authentication.signin.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.SignInViewModel

class SigninViewModelFactory (private var userRepo: UserRepo ): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            SignInViewModel(userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}