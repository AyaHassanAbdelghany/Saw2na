package com.example.mcommerceapp.view.ui.feature_product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.mcommerceapp.databinding.CategorizedProductScreenBinding
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.feature_product.adapter.CategorizedProductAdapter
import com.example.mcommerceapp.view.ui.feature_product.adapter.OnClickListner
import com.example.mcommerceapp.view.ui.feature_product.viewmodel.CategorizedProductVM
import com.example.mcommerceapp.view.ui.feature_product.viewmodel.CategorizedProductVMFactory
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail

class CategorizedProductActivity : AppCompatActivity(), OnClickListner {

    lateinit var binding: CategorizedProductScreenBinding

    private lateinit var productsVM: CategorizedProductVM
    private lateinit var productsVMFactory: CategorizedProductVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CategorizedProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsVMFactory = CategorizedProductVMFactory(ProductRepo.getInstance(RemoteSource()))
        productsVM = ViewModelProvider(this, productsVMFactory)[productsVM::class.java]

        val intent = intent.getBundleExtra("PRODUCTS")
        Log.d("typee", intent?.get("VALUE").toString())
        Log.d("typee", intent?.get("TYPE").toString())

        productsVM.getProduct(intent?.get("VALUE").toString())

        Log.e("Tag", savedInstanceState?.getInt("VENDOR_ID").toString())

        val mainAdapter = CategorizedProductAdapter(this, this)
        productsVM.products.observe(this) {
            mainAdapter.setData(it)
            binding.grid.adapter = mainAdapter
        }
    }

    override fun onClick(id: String) {
        val intent = Intent(this, ProductDetail::class.java)
        intent.putExtra("PRODUCTS_ID", id)
        startActivity(intent)    }
}