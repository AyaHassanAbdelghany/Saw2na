package com.example.mcommerceapp.view.ui.feature_product

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.mcommerceapp.databinding.CategorizedProductScreenBinding
import com.example.mcommerceapp.view.ui.feature_product.adapter.CategorizedProductAdapter

class CategorizedProductActivity : AppCompatActivity() {

    lateinit var binding : CategorizedProductScreenBinding
    lateinit var productList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CategorizedProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


       val intent = intent.getBundleExtra("PRODUCTS")
        Log.d("typee", intent?.get("VALUE").toString())
        Log.d("typee", intent?.get("TYPE").toString())

        Log.e("Tag", savedInstanceState?.getInt("VENDOR_ID").toString())

        productList = listOf("jsbuhsn", "nuhsbnxn", "onduhbfuewnm", "fejiegnfwijm", "odmjrnvdi", "vfnnidnvi")
//        val mainAdapter = CategorizedProductAdapter(this, productList)
//        binding.grid.adapter = mainAdapter

    }
}