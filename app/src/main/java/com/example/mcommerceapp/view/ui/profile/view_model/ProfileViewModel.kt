package com.example.mcommerceapp.view.ui.profile.view_model

import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User

class ProfileViewModel(var userRepo: UserRepo) :ViewModel() {



    fun getUser(): User {
        return userRepo.getUser()
    }

    fun setLoggedInState(state:Boolean){
        userRepo.setLoggedInState(state)
    }
    fun getLoggedInState():Boolean{
        return userRepo.getLoggedInState()
    }

    fun setUser(user: User){
        userRepo.setUser(user)
    }

}