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
    private var categoryList: MutableList<String> = mutableListOf()
    private var vendorList: MutableList<String> = mutableListOf()
    private var productList: MutableList<Products> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
//        searchVM.category.observe(this) {
//            for (cat in it) {
//                searchList.add(cat.productType)
//                categoryList.add(cat.productType)
//                Log.d("Search", categoryList.count().toString())
//            }
//        }

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
            binding.foundTxt.visibility = View.INVISIBLE
            val chooseWord = binding.searchEditTxt.text.toString()
            Log.d("Search", chooseWord)
            when {
//                categoryList.contains(chooseWord) -> {
//                    Log.d("search", "cat")
//                    bundle.putString("TYPE", Keys.COLLECTION)
//                    bundle.putString("SUB_CATEGORY", chooseWord)
//                    val intent = Intent(this, CategorizedProductActivity::class.java)
//                    intent.putExtra("PRODUCTS", bundle)
//                    startActivity(intent)
//                }
                vendorList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.VENDOR)
                    bundle.putString("VALUE", chooseWord.toString())
                    val intent = Intent(this, CategorizedProductActivity::class.java)
                    intent.putExtra("PRODUCTS", bundle)
                    startActivity(intent)
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