package com.example.mcommerceapp.view.ui.feature_product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.CategorizedProductScreenBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.MainActivity
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteScreen
import com.example.mcommerceapp.view.ui.feature_product.adapter.CategorizedProductAdapter
import com.example.mcommerceapp.view.ui.feature_product.adapter.OnClickListner
import com.example.mcommerceapp.view.ui.feature_product.viewmodel.CategorizedProductVM
import com.example.mcommerceapp.view.ui.feature_product.viewmodel.CategorizedProductVMFactory
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail
import com.example.mcommerceapp.view.ui.search.view.SearchActivity
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider

class CategorizedProductActivity : AppCompatActivity(), OnClickListner {

    lateinit var binding: CategorizedProductScreenBinding

    private var minValue = 100.0
    private var maxValue = 2000.0
    private lateinit var products: ArrayList<Products>
    private var checkboxText: ArrayList<String> = arrayListOf()
    private lateinit var checkboxT_Shirt: CheckBox
    private lateinit var checkboxAccessories: CheckBox
    private lateinit var checkboxShoes: CheckBox

    private lateinit var productsVM: CategorizedProductVM
    private lateinit var productsVMFactory: CategorizedProductVMFactory
    private lateinit var value: String
    private lateinit var type :String
    private lateinit var categoryProductAdapter: CategorizedProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CategorizedProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


        MyConnectivityManager.state.observe(this) {

            if (it) {
                Toast.makeText(this, "Connection is restored", Toast.LENGTH_SHORT).show()
                checkType()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                Toast.makeText(this, "Connection is lost", Toast.LENGTH_SHORT).show()
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
       val intent = intent.getBundleExtra("PRODUCTS")
        value = intent?.getString("VALUE", " ") ?: ""
         type = intent?.get("TYPE").toString()

        binding.actionBarLayout.backImg.visibility = ImageView.VISIBLE

        binding.actionBarLayout.backImg.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }

        binding.actionBarLayout.favouriteImage.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FavoriteScreen::class.java
                )
            )
        }

        binding.actionBarLayout.cardImage.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ShoppingCartScreen::class.java
                )
            )
        }

        binding.actionBarLayout.searchImage.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SearchActivity::class.java
                )
            )
        }

        productsVM.products.observe(this) {
            categoryProductAdapter.setData(it, productsVM.currencySymbol, productsVM.currencyValue)
            binding.grid.adapter = categoryProductAdapter
            binding.filterImageView.setOnClickListener {
                showSupportBottomSheet()
            }

        }


        binding.filterImageView.setOnClickListener {
            showSupportBottomSheet()

            productsVM.products.observe(this) {
                categoryProductAdapter.setData(
                    it,
                    productsVM.currencySymbol,
                    productsVM.currencyValue
                )
                binding.grid.adapter = categoryProductAdapter

            }

        }
    }

    private fun checkType(){
        when (type) {
            Keys.VENDOR -> observeVendor()
            Keys.ALL_PRODUCT -> observeAllProducts()
        }
    }
        private fun observeVendor() {
            productsVM.getProductsVendor(value)
            observeProducts()
    private fun observeVendor() {
        productsVM.getProductsVendor(value)
        observeProducts()
    }

    private fun observeProducts() {
        productsVM.products.observe(this) {
            products = it

            categoryProductAdapter.setData(it, productsVM.currencySymbol, productsVM.currencyValue)
            binding.grid.adapter = categoryProductAdapter
=        }
    }

    private fun observeAllProducts() {
        productsVM.allProducts.observe(this) {


            products = it
            categoryProductAdapter.setData(it, productsVM.currencySymbol, productsVM.currencyValue)

            categoryProductAdapter.setData(
                it,
                productsVM.currencySymbol,
                productsVM.currencyValue
            )

            binding.grid.adapter = categoryProductAdapter
        }
    }

    private fun init() {
        productsVMFactory = CategorizedProductVMFactory(
            ProductRepo.getInstance(RemoteSource()),
            CurrencyRepo.getInstance(RemoteSource(), this)
        )
        productsVM =
            ViewModelProvider(this, productsVMFactory)[CategorizedProductVM::class.java]
        categoryProductAdapter = CategorizedProductAdapter(this, this)
    }

    override fun onClick(id: String) {
        val intent = Intent(this, ProductDetail::class.java)
        intent.putExtra("PRODUCTS_ID", id)
        startActivity(intent)
    }

    private fun filterProducts() {
        val filterProducts: ArrayList<Products> = arrayListOf()

        if (checkboxText.size == 0) {
            checkboxText.add(checkboxT_Shirt.text.toString())
            checkboxText.add(checkboxAccessories.text.toString())
            checkboxText.add(checkboxShoes.text.toString())
        }

        for (index in 0 until this.products.size) {
            if ((products[index].variants[0].price?.toDouble()!! >= this.minValue)
                && (this.maxValue >= this.products[index].variants[0].price!!.toDouble())
                && (checkboxText.contains(products[index].productType))

            ) {
                filterProducts.add(products[index])
            }
        }
        if (filterProducts.size > 0) {
            Log.e("filter", "hello")

            categoryProductAdapter.setData(
                filterProducts,
                productsVM.currencySymbol,
                productsVM.currencyValue
            )
        } else {
            Log.e("no filter", "hello")
            categoryProductAdapter.setData(
                this.products,
                productsVM.currencySymbol,
                productsVM.currencyValue
            )
        }
    }

    @SuppressLint("InflateParams")
    fun showSupportBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

        val seekBar = view.findViewById<RangeSlider>(R.id.seekBar)
        checkboxT_Shirt = view.findViewById(R.id.t_shirt_checkbox)
        checkboxAccessories = view.findViewById(R.id.accessories_checkbox)
        checkboxShoes = view.findViewById(R.id.shoes_checkbox)
        val btnSubmit = view.findViewById<Button>(R.id.submitBtn)


        productsVM.subCategory.observe(this) {
            checkboxT_Shirt.text = it.elementAt(0).productType
            checkboxAccessories.text = it.elementAt(1).productType
            checkboxShoes.text = it.elementAt(2).productType
        }

        btnSubmit.setOnClickListener {
            checkboxText.clear()
            if (checkboxT_Shirt.isChecked) {
                checkboxText.add(checkboxT_Shirt.text.toString())
            }
            if (checkboxAccessories.isChecked) {
                checkboxText.add(checkboxAccessories.text.toString())
            }
            if (checkboxShoes.isChecked) {
                checkboxText.add(checkboxShoes.text.toString())
            }

            filterProducts()
            dialog.dismiss()
        }

        seekBar.setValues(1.0f, 2000.0f)
        seekBar.stepSize = 0f

        seekBar.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                this@CategorizedProductActivity.minValue =
                    String.format("%.2f", values[0]).toDouble()
                this@CategorizedProductActivity.maxValue =
                    String.format("%.2f", values[1]).toDouble()
            }
        })

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

}
