package com.example.mcommerceapp.model.user_repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.FirebaseAuthRepo
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.LocalUserInfoRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.google.firebase.auth.FirebaseAuth

class UserRepo private constructor(private val context: Context) : FirebaseAuthRepo , LocalUserInfoRepo {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user", 0)

    private val _signUpAuthState by lazy { MutableLiveData<String>() }
    val signUpAuthState: LiveData<String> =  _signUpAuthState

    private val _signInAuthState by lazy { MutableLiveData<String>() }
    val signInAuthState: LiveData<String> = _signInAuthState

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private val userRepo: UserRepo? = null
        fun getInstance( context: Context): UserRepo {
            return userRepo ?: UserRepo(context)
        }
    }


    override fun signIn(email : String, password:String){

        Log.i("TAG","Email signin")
        _signInAuthState.value = AuthState.LOADING

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            if (firebaseAuth.currentUser!!.isEmailVerified) {
                Log.i("TAG","Email signin is successful")
                _signInAuthState.value = AuthState.SUCCESS

            } else {
                firebaseAuth.currentUser!!.sendEmailVerification()
                Log.i("TAG","Email signin is successful but not EmailVerified ")
                _signInAuthState.value = AuthState.EMAIL_NOT_VERIFIED
                firebaseAuth.signOut()
            }
        }.addOnFailureListener { e ->
            Log.i("TAG","Email signin is not successful ${e.message}")
            _signInAuthState.value = e.localizedMessage
        }

    }

    override fun signOut(){
        setLoggedInState(false)
        firebaseAuth.signOut()
    }

    override fun register(emailAddress: String?, password: String?)  {

        firebaseAuth.createUserWithEmailAndPassword(emailAddress!!, password!!).addOnSuccessListener {
            Log.i("TAG", "onSuccess:register done ")
            firebaseAuth.currentUser!!.sendEmailVerification().addOnSuccessListener {
                Log.i("TAG", "onSuccess:sendEmailVerification ")

            }.addOnFailureListener {
                Log.i("TAG", "OnFailure:sendEmailVerification ")

            }
                .addOnSuccessListener {
                    _signUpAuthState.value = AuthState.SUCCESS
                    firebaseAuth.signOut()
                }
                .addOnFailureListener { e ->
                    _signUpAuthState.value = e.localizedMessage

                }
        }.addOnFailureListener { e ->
            _signUpAuthState.value = e.localizedMessage
        }

    }

    override fun setLoggedInState(state :Boolean){
      sharedPreferences.edit().putBoolean("loggedIn",state).apply()
    }

    override fun setUser(user: User){
        sharedPreferences.edit().putString("name", user.displayName)
            .putString("email",user.email).apply()
    }

    override fun getLoggedInState() :Boolean{
        return sharedPreferences.getBoolean("loggedIn",false)
    }

    override fun getUser():User{
        val name = sharedPreferences.getString("name","no name")
        val email = sharedPreferences.getString("email","no email")
        return User(name!!,email!!)
    }

}