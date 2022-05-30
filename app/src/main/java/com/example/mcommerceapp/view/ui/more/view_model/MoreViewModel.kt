package com.example.mcommerceapp.view.ui.more.view_model

import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.google.firebase.auth.FirebaseAuth

class MoreViewModel(private val userRepo: UserRepo) : ViewModel() {

    fun signOut(){
        userRepo.setLoggedInState(false)
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }

}