package com.example.mcommerceapp.view.ui.authentication.signin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySigninBinding
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.SignInViewModel
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.factory.SigninViewModelFactory
import com.example.mcommerceapp.view.ui.authentication.signup.view.SignUpActivity


class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var signinButton : Button
    private lateinit var loading : ProgressBar
    private lateinit var signup : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        signinButton = binding.signinButton
        loading = binding.loadingProgressBar
        signup = binding.signupTextView

        val viewModelFactory = SigninViewModelFactory(UserRepo.getInstance( getSharedPreferences("user", MODE_PRIVATE)))
        val signinViewModel = ViewModelProvider(this,viewModelFactory)[SignInViewModel::class.java]

        signinViewModel.authState.observe(this){
            loading.visibility = View.INVISIBLE
            when(it){
                AuthState.SUCCESS ->{
                    signinViewModel.setLoggedInState(true)
                    val name = signinViewModel.getUser().displayName
                    Toast.makeText(this, "welcome $name...", Toast.LENGTH_SHORT).show()
                }
                AuthState.EMAIL_NOT_VERIFIED ->{
                    Toast.makeText(this, "please verify your email...", Toast.LENGTH_SHORT).show()
                }
                AuthState.LOADING -> loading.visibility = View.VISIBLE
                else ->{
                    Log.i("TAG", "onCreate: AuthState.error :${it} ")
                    Toast.makeText(this, "${it}...", Toast.LENGTH_SHORT).show()
                }
            }
        }



        signinButton.setOnClickListener{
            if (isEmailValid() && isPasswordValid() ){
                loading.visibility = View.VISIBLE
                signinViewModel.signIn(emailEditText.text.toString(), passwordEditText.text.toString())
            }

        }

        signup.setOnClickListener{
            startActivity(Intent(this , SignUpActivity::class.java))
        }

    }

    private fun isEmailValid(): Boolean {
        val username = emailEditText.text.toString()
        return if (username.contains('@') && Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            true
        } else {
            emailEditText.error = "Email is not valid"
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(): Boolean {
        return if ( passwordEditText.text.toString().length > 5){
            true
        }else{
            passwordEditText.error = "password must be at least 6 char"
            false
        }
    }

}