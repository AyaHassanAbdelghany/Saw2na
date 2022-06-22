package com.example.mcommerceapp.view.ui.search

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivitySearchBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail


class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    private lateinit var searchVM: SearchViewModel
    private lateinit var searchVMFactory: SearchViewModelFactory
    private var searchList: ArrayList<String> = arrayListOf()
    private var vendorList: ArrayList<String> = arrayListOf()
    private var productList: ArrayList<Products> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.foundTxt.visibility = View.INVISIBLE
        init()

        searchVM.smartCollection.observe(this) {
            for (vendor in it) {
                vendor.title?.let { it1 -> searchList.add(it1) }
                vendor.title?.let { it1 -> vendorList.add(it1) }
            }
        }

        searchVM.allProducts.observe(this) {
            productList = it
            for (product in it) {
                searchList.add(product.title!!)
            }
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.select_dialog_item, searchList)
        binding.searchEditTxt.threshold = 1
        binding.searchEditTxt.setAdapter(adapter)
        binding.searchIcon.setOnClickListener {
            val bundle = Bundle()
            val chooseWord = binding.searchEditTxt.text.toString()
            Log.d("Search", chooseWord)
            when {
                vendorList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.VENDOR)
                    bundle.putString("VALUE", chooseWord.toString())
                    val intent = Intent(this, CategorizedProductActivity::class.java)
                    intent.putExtra("PRODUCTS", bundle)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    for (index in productList) {
                        if (index.title == chooseWord) {
                            val intent = Intent(this, ProductDetail::class.java)
                            intent.putExtra("PRODUCTS_ID", index.id.toString())
                            startActivity(intent)
                        }else{
                            binding.foundTxt.visibility = View.VISIBLE
                        }
                    }
                    finish()
                }
            }
        }
        binding.cancelTxt.setOnClickListener { finish() }
    }

    private fun init() {
        searchVMFactory = SearchViewModelFactory()
        searchVM = ViewModelProvider(this, searchVMFactory)[SearchViewModel::class.java]
    }
}