package com.example.mcommerceapp.view.ui.authentication.signup.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(val userRepo: UserRepo) : ViewModel() {

    val authState: LiveData<String> = userRepo.signUpAuthState

    val finish: LiveData<Boolean> = userRepo.finish

    fun register(emailAddress: String?, password: String?) {
        userRepo.register(emailAddress, password)
    }

    fun setUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.setUser(user)
        }
    }

}