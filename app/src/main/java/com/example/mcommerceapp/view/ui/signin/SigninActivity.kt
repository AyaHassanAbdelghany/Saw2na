package com.example.mcommerceapp.view.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySigninBinding
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.ShopifyRepo
import com.example.mcommerceapp.view.ui.signup.SignUpActivity


class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.usernameEditText
        val password = binding.passwordEditText
        val signinButton = binding.signinButton
        val loading = binding.loadingProgressBar
        val signup = binding.signupTextView

        signinButton.setOnClickListener{
            //loading.visibility = View.VISIBLE
            Log.i("TAG", "signinButton: ")
        }
        signup.setOnClickListener{
            startActivity(Intent(this , SignUpActivity::class.java))
        }



    }
}