package com.example.mcommerceapp.view.ui.authentication.signup.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User

class SignupViewModel(val userRepo: UserRepo) : ViewModel() {

    val authState: LiveData<String> =  userRepo.signUpAuthState

    fun register(emailAddress: String?, password: String?) {
       userRepo.register(emailAddress,password)
    }

    fun setUser(user: User){
        userRepo.setUser(user)
    }

}