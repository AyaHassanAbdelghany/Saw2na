package com.example.mcommerceapp.view.ui.feature_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mcommerceapp.databinding.CategorizedProductScreenBinding
import com.example.mcommerceapp.pojo.products.Products

class CategorizedProductActivity : AppCompatActivity() {

    lateinit var binding : CategorizedProductScreenBinding
    lateinit var productList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CategorizedProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = listOf("jsbuhsn", "nuhsbnxn", "onduhbfuewnm", "fejiegnfwijm", "odmjrnvdi", "vfnnidnvi")
        val mainAdapter = CategorizedProductAdapter(this, productList)
        binding.grid.adapter = mainAdapter

    }
}