package com.example.mcommerceapp.view.ui.authentication.signin.view_model.factory

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.SigninViewModel

class SigninViewModelFactory (private var userRepo: UserRepo ): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SigninViewModel::class.java)) {
            SigninViewModel(userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}