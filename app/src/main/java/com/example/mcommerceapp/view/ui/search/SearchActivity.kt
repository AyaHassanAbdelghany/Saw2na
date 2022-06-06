package com.example.mcommerceapp.view.ui.search

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivitySearchBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity


class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    private lateinit var searchVM: SearchViewModel
    private lateinit var searchVMFactory: SearchViewModelFactory
    private var searchList: ArrayList<String> = arrayListOf()
    private var searchTypeList: ArrayList<String> = arrayListOf()
    private var categoryList: MutableList<String> = mutableListOf()
    private var vendorList: MutableList<String> = mutableListOf()
    private var productList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        searchVM.category.observe(this) {
            Log.d("count", it.toString())
            for (cat in it) {
                searchList.add(cat.productType)
                categoryList.add(cat.productType)
            }
        }

        searchVM.smartCollection.observe(this) {
            Log.d("count", it.toString())
            for (vendor in it) {
                vendor.title?.let { it1 -> searchList.add(it1) }
                vendor.title?.let { it1 -> vendorList.add(it1) }
            }
        }

        searchVM.products.observe(this){
            for (product in it) {
                product.title?.let { it1 -> searchList.add(it1) }
                product.title?.let { it1 -> productList.add(it1) }
            }
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.select_dialog_item, searchList)
        binding.searchEditTxt.threshold = 1
        binding.searchEditTxt.setAdapter(adapter)
        Log.d("text", binding.searchEditTxt.text.toString())

        binding.searchEditTxt.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                Log.i("TAG", "Enter pressed")
            }
            false
        }

        binding.searchEditTxt.setOnItemClickListener { adapterView, view, position, id ->
            Log.d("tag000", position.toString())
            val bundle = Bundle()
            val chooseWord = adapterView.getItemAtPosition(position)
            when {
                categoryList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.COLLECTION)
                    bundle.putString("SUB_CATEGORY", chooseWord.toString())
                }

                vendorList.contains(chooseWord) -> {
                    bundle.putString("TYPE", Keys.VENDOR)
                    bundle.putString("VENDOR", chooseWord.toString())
                }
                else -> {
                    intent.putExtra("TYPE", Keys.PRODUCT)
                }
            }
            val intent = Intent(this, CategorizedProductActivity::class.java)
            intent.putExtra("PRODUCTS", bundle)
            startActivity(intent)
        }
        binding.cancelTxt.setOnClickListener { finish() }
    }

    private fun init() {
        searchVMFactory = SearchViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        searchVM = ViewModelProvider(this, searchVMFactory)[SearchViewModel::class.java]
    }
}