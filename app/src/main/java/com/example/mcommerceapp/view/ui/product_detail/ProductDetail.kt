package com.example.mcommerceapp.view.ui.product_detail

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcommerceapp.databinding.ActivityProductDetailBinding
import com.ms.square.android.expandabletextview.ExpandableTextView


class ProductDetail : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding
    private lateinit var imageSliderPager: ImageSlideAdapter
    private lateinit var sizeAdapter: SizeAdapter
    lateinit var colorAdapter: ColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageSliderPager = ImageSlideAdapter(this,
            listOf("https://cdn.shopify.com/s/files/1/0589/7509/2875/products/8cd561824439482e3cea5ba8e3a6e2f6.jpg?v=1653403052", "https://cdn.shopify.com/s/files/1/0589/7509/2875/products/8cd561824439482e3cea5ba8e3a6e2f6.jpg?v=1653403052", "https://cdn.shopify.com/s/files/1/0589/7509/2875/products/8cd561824439482e3cea5ba8e3a6e2f6.jpg?v=1653403052"))
        binding.viewPagerMain.adapter = imageSliderPager
        binding.indicator.setViewPager(binding.viewPagerMain)

        binding.contentDetail.sizeRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sizeAdapter = SizeAdapter(listOf("S", "M", "L", "XL"), this)
        binding.contentDetail.sizeRecycleView.adapter = sizeAdapter

        binding.contentDetail.colorRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        colorAdapter = ColorAdapter(listOf("blue", "red", "green", "black"), this)
        binding.contentDetail.colorRecycleView.adapter = colorAdapter


        binding.contentDetail.readMore.text = "Copyright 2014 Manabu Shimobe\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."
    }
}