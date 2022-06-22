package com.example.mcommerceapp.view.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mcommerceapp.R
import com.example.mcommerceapp.view.MainActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()

        Timer().schedule(timerTask {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }, 5000)
    }
}