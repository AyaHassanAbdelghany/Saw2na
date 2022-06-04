package com.example.mcommerceapp.model.user_repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.model.remote_source.customer.CustomerRemoteSource
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.FirebaseAuthRepo
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.LocalUserInfoRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserRepo private constructor(context: Context) : FirebaseAuthRepo,
    LocalUserInfoRepo {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user", 0)

    private val _signUpAuthState by lazy { MutableLiveData<String>() }
    val signUpAuthState: LiveData<String> = _signUpAuthState

    private val _signInAuthState by lazy { MutableLiveData<String>() }
    val signInAuthState: LiveData<String> = _signInAuthState

    private val _user by lazy { MutableLiveData<User>() }
    val user: LiveData<User> = _user

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val customerRemoteSource = CustomerRemoteSource()


    companion object {
        private val userRepo: UserRepo? = null
        fun getInstance(context: Context): UserRepo {
            return userRepo ?: UserRepo(context)
        }
    }


    override fun signIn(email: String, password: String) {

        Log.i("TAG", "Email signin")
        _signInAuthState.value = AuthState.LOADING

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            if (firebaseAuth.currentUser!!.isEmailVerified) {
                Log.i("TAG", "Email signin is successful")
                _signInAuthState.value = AuthState.SUCCESS
                sharedPreferences.edit().putString("email", email).apply()

            } else {
                firebaseAuth.currentUser!!.sendEmailVerification()
                Log.i("TAG", "Email signin is successful but not EmailVerified ")
                _signInAuthState.value = AuthState.EMAIL_NOT_VERIFIED
                firebaseAuth.signOut()
            }
        }.addOnFailureListener { e ->
            Log.i("TAG", "Email signin is not successful ${e.message}")
            _signInAuthState.value = e.localizedMessage
        }

    }

    override fun signOut() {
        setLoggedInState(false)
        firebaseAuth.signOut()
    }

    override fun register(emailAddress: String?, password: String?) {

        firebaseAuth.createUserWithEmailAndPassword(emailAddress!!, password!!)
            .addOnSuccessListener {
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

    override fun setLoggedInState(state: Boolean) {
        sharedPreferences.edit().putBoolean("loggedIn", state).apply()
    }

    override suspend fun setUser(user: User) {
        sharedPreferences.edit().putString("name", user.displayName)
            .putString("email", user.email).apply()

        val req = getRequest(user)
        user.userID = customerRemoteSource.createCustomer(req)
        addToFireStore(user)
    }

    override fun getLoggedInState(): Boolean {
        return sharedPreferences.getBoolean("loggedIn", false)
    }

    override fun getUser(): User {
        val name = sharedPreferences.getString("name", "no name")
        val email = sharedPreferences.getString("email", "no email")
        return User(name!!, email!!)
    }


    private fun addToFireStore(user: User) {
        fireStore.collection("users").document(user.email).set(user)
    }

    override fun retrieveUserFromFireStore(): LiveData<User> {
        fireStore.collection("users").document(getUser().email).get().addOnSuccessListener { d ->
            val u = d.toObject(User::class.java)
            _user.value = u
        }
        return user
    }

    private fun getRequest(user: User): RequestBody {
        val names = user.displayName.split(" ")

        val json = JSONObject()

        json.put("first_name", names[0])
        if (names.size > 1)
            json.put("last_name", names[1])
        else
            json.put("last_name", " null")

        json.put("email", user.email)
        json.put("verified_email", true)

        val req = JSONObject()
        req.put("customer", json)

        val requestBody = req.toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.i("customer", "retrieveUserFromFireStore:  $req ")

        return requestBody
    }

    override fun setLanguage(lan:String){
        sharedPreferences.edit().putString("lan", lan).apply()
    }
}