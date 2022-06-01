package com.example.mcommerceapp.view.ui.more.view_model

import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.FirebaseAuthRepo

class MoreViewModel(private val userRepo: FirebaseAuthRepo) : ViewModel() {

    fun signOut(){
        userRepo.signOut()
    }

}