package com.example.mcommerceapp.view.ui.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.mcommerceapp.MainActivity
import com.example.mcommerceapp.R
import java.util.*
import kotlin.concurrent.timerTask

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(timerTask {
            startActivity(Intent(applicationContext,MainActivity::class.java))
        }, 5000)
    }
}