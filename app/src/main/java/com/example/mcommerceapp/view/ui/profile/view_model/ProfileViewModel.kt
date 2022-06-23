package com.example.mcommerceapp.view.ui.profile.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.LocalUserInfoRepo
import com.example.mcommerceapp.pojo.user.User

class ProfileViewModel(var userRepo: LocalUserInfoRepo) : ViewModel() {


    fun getUser(): User {
        return userRepo.getUser()
    }

    fun getLoggedInState(): Boolean {
        return userRepo.getLoggedInState()
    }

    fun retrieveUserFromFireStore(): LiveData<User> {
        return userRepo.retrieveUserFromFireStore()
    }

}