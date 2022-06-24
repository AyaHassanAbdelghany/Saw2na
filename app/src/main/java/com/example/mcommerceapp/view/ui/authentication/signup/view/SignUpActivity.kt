package com.example.mcommerceapp.view.ui.authentication.signup.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySignUpBinding
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.authentication.AuthState
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.authentication.signup.view_model.SignupViewModel
import com.example.mcommerceapp.view.ui.authentication.signup.view_model.factory.SignupViewModelFactory

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

        initViews()

        val viewModelFactory = SignupViewModelFactory(UserRepo.getInstance(this))
        val viewModel = ViewModelProvider(this, viewModelFactory)[SignupViewModel::class.java]

        viewModel.authState.observe(this) {
            loading.visibility = View.INVISIBLE
            when (it) {
                AuthState.SUCCESS -> {
                    Toast.makeText(this, "please check your mail ..", Toast.LENGTH_SHORT).show()

                    val user = User(
                        displayNameEditText.text.toString(),
                        emailEditText.text.toString(),
                        true
                    )
                    viewModel.setUser(user)
                    loading.visibility = View.VISIBLE
                }

                AuthState.LOADING -> loading.visibility = View.VISIBLE
                else -> {
                    Log.i("TAG", "onCreate: AuthState.error :${it} ")
                    Toast.makeText(this, "${it}...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.finish.observe(this) {
            loading.visibility = View.INVISIBLE
            finish()
        }


        signupButton.setOnClickListener {

            if (isUserNameValid() && isEmailValid() && isPasswordValid()) {
                loading.visibility = View.VISIBLE
                viewModel.register(emailEditText.text.toString(), passwordEditText.text.toString())
            }
        }
        signinTextView.setOnClickListener {
            finish()
            startActivity(Intent(this, SigninActivity::class.java))
        }

    }

    private fun initViews() {
        displayNameEditText = binding.displayNameEditText
        emailEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        confirmPasswordEditText = binding.retypePasswordEditText

        signupButton = binding.signupButton
        loading = binding.loadingProgressBar
        signinTextView = binding.signinTextView
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