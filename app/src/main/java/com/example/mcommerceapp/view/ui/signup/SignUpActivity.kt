package com.example.mcommerceapp.view.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySignUpBinding
import com.example.mcommerceapp.databinding.ActivitySigninBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = binding.usernameEditText
        val password = binding.passwordEditText
        val signupButton = binding.signupButton
        val loading = binding.loadingProgressBar
        val signinTextView = binding.signinTextView

        signupButton.setOnClickListener{
            loading.visibility = View.VISIBLE
            Log.i("TAG", "signupButton: ")
        }
        signinTextView.setOnClickListener{
            finish()
        }

    }
}