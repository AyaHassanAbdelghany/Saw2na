package com.example.mcommerceapp.model.shopify_repository.user.user_repo_interfaces

import androidx.lifecycle.LiveData
import com.example.mcommerceapp.pojo.user.User

interface FirebaseAuthRepo {

    fun signIn(email: String, password: String)

    fun signOut()

    fun register(emailAddress: String?, password: String?)

}


interface LocalUserInfoRepo {

    fun setLoggedInState(state: Boolean)

    suspend fun setUser(user: User)

    fun getLoggedInState(): Boolean

    fun getUser(): User

    fun retrieveUserFromFireStore(): LiveData<User>

    fun setLanguage(lan: String)
}

interface GetUserCartRepo {
    fun getUser(): User
    fun retrieveUserFromFireStore(): LiveData<User>
}