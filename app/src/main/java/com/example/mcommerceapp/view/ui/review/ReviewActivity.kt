package com.example.mcommerceapp.view.ui.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mcommerceapp.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityReviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}