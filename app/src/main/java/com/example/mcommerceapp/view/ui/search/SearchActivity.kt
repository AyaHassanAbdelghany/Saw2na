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
        searchVM.category.observe(this) {
            Log.d("count", it.toString())
            for (cat in it) {
                searchList.add(cat.productType.lowercase())
                categoryList.add(cat.productType.lowercase())
            }
        }

        searchVM.smartCollection.observe(this) {
            Log.d("count", it.toString())
            for (vendor in it) {
                vendor.title?.lowercase().let { it1 -> searchList.add(it1!!) }
                vendor.title?.lowercase().let { it1 -> vendorList.add(it1!!) }
            }
        }

        searchVM.getAllProducts()
        searchVM.allProducts.observe(this) {
            productList = it
            for (product in it) {
                Log.d("product0000000000000000", it.toString())
                searchList.add(product.title?.lowercase()!!)
//                product.title?.lowercase().let { it1 -> searchList.add(it1!!) }
//                product.title?.let { it1 -> productList.add(it1) }
            }
        }

        Log.d("list", searchList.toString())

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.select_dialog_item, searchList)
        binding.searchEditTxt.threshold = 1
        binding.searchEditTxt.setAdapter(adapter)
        Log.d("text", binding.searchEditTxt.text.toString())
        binding.searchIcon.setOnClickListener {
            val bundle = Bundle()
            var i: Int = 0
            binding.foundTxt.visibility = View.INVISIBLE
            val chooseWord = binding.searchEditTxt.text.toString()
            Log.d("wwwww", chooseWord)
            when {
                categoryList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.COLLECTION)
                    bundle.putString("SUB_CATEGORY", chooseWord.toString())
                    val intent = Intent(this, CategorizedProductActivity::class.java)
                    intent.putExtra("PRODUCTS", bundle)
                    startActivity(intent)
                }
                vendorList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.VENDOR)
                    bundle.putString("VENDOR", chooseWord.toString())
                    val intent = Intent(this, CategorizedProductActivity::class.java)
                    intent.putExtra("PRODUCTS", bundle)
                    startActivity(intent)
                }
                else -> {
                    for (index in productList) {
                        if (index.title?.lowercase() == chooseWord) {
                            val intent = Intent(this, ProductDetail::class.java)
                            intent.putExtra("PRODUCTS_ID", index.id)
                            startActivity(intent)
                        }else{
                            binding.foundTxt.visibility = View.VISIBLE
                        }
                    }
                }
            }
            binding.searchEditTxt.setOnItemClickListener { adapterView, view, position, id ->
                Log.d("tag000", position.toString())

            }
        }


        binding.cancelTxt.setOnClickListener { finish() }
    }

    private fun init() {
        searchVMFactory = SearchViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        searchVM = ViewModelProvider(this, searchVMFactory)[SearchViewModel::class.java]
    }
}