package com.example.mcommerceapp.model.user_repository

import android.content.SharedPreferences
import com.example.mcommerceapp.pojo.user.User

class UserRepo private constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private val userRepo: UserRepo? = null
        fun getInstance(sharedPreferences: SharedPreferences): UserRepo {
            return userRepo ?: UserRepo(sharedPreferences)
        }
    }

    fun setLoggedInState(state: Boolean) {
        sharedPreferences.edit().putBoolean("loggedIn", state).apply()
    }

    fun setUser(user: User) {
        sharedPreferences.edit().putString("name", user.displayName)
            .putString("email", user.email).apply()
    }

    fun getLoggedInState(): Boolean {
        return sharedPreferences.getBoolean("loggedIn", false)
    }

    fun getUser(): User {
        val name = sharedPreferences.getString("name", "no name")
        val email = sharedPreferences.getString("email", "no email")
        return User(name!!, email!!)
    }

}