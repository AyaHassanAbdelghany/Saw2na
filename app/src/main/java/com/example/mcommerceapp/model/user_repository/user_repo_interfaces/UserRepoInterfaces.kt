package com.example.mcommerceapp.model.user_repository.user_repo_interfaces

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState

interface FirebaseAuthRepo{

    fun signIn(email : String , password:String)

    fun signOut()

    fun register(emailAddress: String?, password: String?)

}


interface LocalUserInfoRepo{

    fun setLoggedInState(state :Boolean)

    suspend fun setUser(user: User)

    fun getLoggedInState() :Boolean

    fun getUser(): User

    fun retrieveUserFromFireStore(): LiveData<User>

    fun setLanguage(lan:String)
}