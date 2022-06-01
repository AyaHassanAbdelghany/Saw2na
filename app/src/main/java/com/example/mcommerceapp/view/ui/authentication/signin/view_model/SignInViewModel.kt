package com.example.mcommerceapp.view.ui.authentication.signin.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User


class SignInViewModel(var userRepo: UserRepo) : ViewModel() {

    val authState: LiveData<String> = userRepo.signInAuthState

    fun signIn(email : String, password:String){
        userRepo.signIn(email,password)
    }

    fun getUser():User{
        return userRepo.getUser()
    }

    fun setLoggedInState(state:Boolean){
        userRepo.setLoggedInState(state)
    }


}


