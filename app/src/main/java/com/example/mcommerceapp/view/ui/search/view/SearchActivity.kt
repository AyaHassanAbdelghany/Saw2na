package com.example.mcommerceapp.view.ui.search.view

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivitySearchBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail
import com.example.mcommerceapp.view.ui.search.viewmodel.SearchViewModel
import com.example.mcommerceapp.view.ui.search.viewmodel.SearchViewModelFactory


class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    private lateinit var searchVM: SearchViewModel
    private lateinit var searchVMFactory: SearchViewModelFactory
    private var searchList: ArrayList<String> = arrayListOf()
    private var vendorList: MutableList<String> = mutableListOf()
    private var productList: MutableList<Products> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        MyConnectivityManager.state.observe(this) {
            if (it) {
                Toast.makeText(this, "Connection is restored", Toast.LENGTH_SHORT).show()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                Toast.makeText(this, "Connection is lost", Toast.LENGTH_SHORT).show()
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
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
            when {
                vendorList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.VENDOR)
                    bundle.putString("VALUE", chooseWord)
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

                        } else {
                            binding.foundTxt.visibility = View.VISIBLE
                        }
                    }
                    binding.foundTxt.visibility = View.VISIBLE
                }
            }
        }
        binding.backImage.setOnClickListener { finish() }
    }

    private fun init() {
        searchVMFactory = SearchViewModelFactory()
        searchVM = ViewModelProvider(this, searchVMFactory)[SearchViewModel::class.java]
    }
}