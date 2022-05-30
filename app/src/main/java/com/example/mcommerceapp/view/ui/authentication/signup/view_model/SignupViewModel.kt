package com.example.mcommerceapp.view.ui.authentication.signup.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel(val userRepo: UserRepo) : ViewModel() {

    private val _authState by lazy { MutableLiveData<String>() }
    val authState: LiveData<String> = _authState

    fun register(emailAddress: String?, password: String?) {
        val fAuth = FirebaseAuth.getInstance()

        fAuth.createUserWithEmailAndPassword(emailAddress!!, password!!).addOnSuccessListener {
            Log.i("TAG", "onSuccess:register done ")
            fAuth.currentUser!!.sendEmailVerification()
                .addOnSuccessListener {
                    _authState.value = AuthState.SUCCESS
                }
                .addOnFailureListener { e ->
                    _authState.value = e.localizedMessage

                }
        }.addOnFailureListener { e ->
            _authState.value = e.localizedMessage
        }
    }

    fun setUser(user: User){
        userRepo.setUser(user)
    }

}