package com.example.mcommerceapp.view.ui.authentication.signup.view

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySignUpBinding
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.example.mcommerceapp.view.ui.authentication.signup.view_model.SignupViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var displayNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loading: ProgressBar
    private lateinit var signinTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = SignupViewModel()
        viewModel.authState.observe(this) {
            loading.visibility = View.INVISIBLE
            when (it) {
                AuthState.SUCCESS -> {
                    Toast.makeText(this, "please check your mail ..", Toast.LENGTH_SHORT).show()
                    val data = getSharedPreferences("user", MODE_PRIVATE)
                    data.edit().putString("name", displayNameEditText.text.toString())
                        .putString("email", emailEditText.text.toString()).apply()
                    finish()
                }
                AuthState.LOADING -> loading.visibility = View.VISIBLE
                else -> {
                    Log.i("TAG", "onCreate: AuthState.error :${it} ")
                    Toast.makeText(this, "${it}...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        displayNameEditText = binding.displayNameEditText
        emailEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        confirmPasswordEditText = binding.retypePasswordEditText

        signupButton = binding.signupButton
        loading = binding.loadingProgressBar
        signinTextView = binding.signinTextView

        signupButton.setOnClickListener {

            if (isUserNameValid() && isEmailValid() && isPasswordValid()) {
                loading.visibility = View.VISIBLE
                viewModel.register(emailEditText.text.toString(), passwordEditText.text.toString())
            }
        }
        signinTextView.setOnClickListener {
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

    private fun isUserNameValid(): Boolean {

        return if (displayNameEditText.text.toString().length > 3) {
            true
        } else {
            displayNameEditText.error = "displayName must be at least 4 char"
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(): Boolean {

        if (!isPasswordMatch()) return false

        return if (passwordEditText.text.toString().length > 5) {
            true
        } else {
            passwordEditText.error = "password must be at least 6 char"
            false
        }
    }

    private fun isPasswordMatch(): Boolean {
        return if (passwordEditText.text.toString() == confirmPasswordEditText.text.toString()) {
            true
        } else {
            confirmPasswordEditText.error = "password doesn't match"
            false
        }
    }

}