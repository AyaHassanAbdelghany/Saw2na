package com.example.mcommerceapp.view.ui.authentication.signin.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.google.firebase.auth.FirebaseAuth

class SigninViewModel(var userRepo: UserRepo) : ViewModel() {

    private val _authState by lazy { MutableLiveData<String>() }
    val authState: LiveData<String> = _authState

    fun signin(email : String , password:String){

        Log.i("TAG","Email signin")
        _authState.value = AuthState.LOADING

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {


            if (firebaseAuth.currentUser!!.isEmailVerified) {

                Log.i("TAG","Email signin is successful")
                _authState.value = AuthState.SUCCESS

            } else {

                Log.i("TAG","Email signin is successful but not EmailVerified ")
                _authState.value = AuthState.EMAIL_NOT_VERIFIED

            }
        }.addOnFailureListener { e ->
            Log.i("TAG","Email signin is not successful ${e.message}")
            _authState.value = e.localizedMessage
        }

    }

    fun getUser():User{
        return userRepo.getUser()
    }

    fun setLoggedInState(state:Boolean){
        userRepo.setLoggedInState(state)
    }


}


