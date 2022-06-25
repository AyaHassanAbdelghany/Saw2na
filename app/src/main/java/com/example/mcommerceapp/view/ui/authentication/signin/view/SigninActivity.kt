package com.example.mcommerceapp.view.ui.authentication.signin.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySigninBinding
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.SignInViewModel
import com.example.mcommerceapp.view.ui.authentication.signin.view_model.factory.SigninViewModelFactory
import com.example.mcommerceapp.view.ui.authentication.signup.view.SignUpActivity


class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText

        val viewModelFactory = SigninViewModelFactory(UserRepo.getInstance(this))
        val signinViewModel = ViewModelProvider(this, viewModelFactory)[SignInViewModel::class.java]

        signinViewModel.authState.observe(this) {
            binding.loadingProgressBar.visibility = View.INVISIBLE
            when (it) {
                AuthState.SUCCESS -> {
                    signinViewModel.setLoggedInState(true)
                    Toast.makeText(this, "welcome ...", Toast.LENGTH_SHORT).show()
                    finish()
                }
                AuthState.EMAIL_NOT_VERIFIED -> {
                    Toast.makeText(this, "please verify your email...", Toast.LENGTH_SHORT).show()
                }
                AuthState.LOADING ->   binding.loadingProgressBar.visibility = View.VISIBLE
                else -> {
                    Toast.makeText(this, "${it}...", Toast.LENGTH_SHORT).show()
                }
            }
        }


        MyConnectivityManager.state.observe(this) {
            if (it) {
                Toast.makeText(this, "Connection is restored", Toast.LENGTH_SHORT).show()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                Toast.makeText(this, "Connection is lost", Toast.LENGTH_SHORT).show()
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
        binding.signinButton.setOnClickListener {
            if (isEmailValid() && isPasswordValid()) {
                binding.loadingProgressBar.visibility = View.VISIBLE
                signinViewModel.signIn(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }

        }
        binding.signupTextView.setOnClickListener {
            finish()
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.backImg.setOnClickListener{
            finish()
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
        return if (passwordEditText.text.toString().length > 5) {
            true
        } else {
            passwordEditText.error = "password must be at least 6 char"
            false
        }
    }

}