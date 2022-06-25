package com.example.mcommerceapp.view.ui.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mcommerceapp.databinding.ActivityReviewBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteItemsAdapter
import com.example.mcommerceapp.view.ui.feature_product.adapter.CategorizedProductAdapter
import java.security.Key

class ReviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityReviewBinding
    private lateinit var reviewAdapter: ReviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewAdapter = ReviewAdapter()

    }

    override fun onResume() {
        super.onResume()
        reviewAdapter.setData(Keys.REVIEWS)
        binding.reviewRecycleView.adapter = reviewAdapter
    }
}