package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentCategoryTypeBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.ui.category.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.category.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteScreen
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail
import com.example.mcommerceapp.view.ui.search.SearchActivity
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar


class CategoryTypeFragment() : OnClickListener, Fragment() {

    private var tabTitle: String = ""
    private var minValue = 1.0
    private var maxValue = 2000.0
    private lateinit var products: ArrayList<Products>
    private var checkboxText: ArrayList<String> = arrayListOf()


    private lateinit var binding: FragmentCategoryTypeBinding
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var categoryVMFactory: CategoryViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var checkboxT_Shirt: CheckBox
    private lateinit var checkboxAccessories: CheckBox
    private lateinit var checkboxShoes: CheckBox

    constructor(tabTitle: String) : this() {
        this.tabTitle = tabTitle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryTypeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterImageView.setOnClickListener {
            showSupportBottomSheet()
        }
    }

    override fun onResume() {
        super.onResume()
        when (tabTitle) {
            "ALL" -> observerAllProducts()
            else -> {
                categoryVM.getCollectionId(tabTitle)
                observerCollectionId()
            }
        }

    }

    private fun observerCollectionId() {
        categoryVM.customCollection.observe(viewLifecycleOwner) {
            categoryVM.getCollectionProducts(it[0].id.toString())
            observerCollectionProducts()
        }
    }

    private fun observerCollectionProducts() {
        categoryVM.collectionProducts.observe(viewLifecycleOwner) {
            products = it
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it, categoryVM.currencySymbol, categoryVM.currencyValue)
            binding.recyclerListCategory.adapter = categoryAdapter
        }
    }

    private fun observerAllProducts() {
        categoryVM.allProducts.observe(viewLifecycleOwner) {
            products = it
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it, categoryVM.currencySymbol, categoryVM.currencyValue)
            binding.recyclerListCategory.adapter = categoryAdapter
        }
    }


    private fun init() {
        categoryVMFactory = CategoryViewModelFactory(ProductRepo.getInstance(RemoteSource()), CurrencyRepo.getInstance(
            RemoteSource(), requireContext
        ()))
        categoryVM = ViewModelProvider(this, categoryVMFactory)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter(requireContext(), this)
    }

    override fun onClick(value: String) {
        val intent = Intent(requireContext(), ProductDetail::class.java)
        intent.putExtra("PRODUCTS_ID", value)
        startActivity(intent)

    }

    @SuppressLint("InflateParams")
    fun showSupportBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

        val seekBar = view.findViewById<RangeSeekBar<Float>>(R.id.seekBar)
        checkboxT_Shirt = view.findViewById<CheckBox>(R.id.t_shirt_checkbox)
        checkboxAccessories = view.findViewById<CheckBox>(R.id.accessories_checkbox)
        checkboxShoes = view.findViewById<CheckBox>(R.id.shoes_checkbox)
        val btnSubmit = view.findViewById<Button>(R.id.submitBtn)


        categoryVM.subCategory.observe(viewLifecycleOwner) {
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

        seekBar.setRangeValues(1.0F, 2000.0F)

        seekBar.setOnRangeSeekBarChangeListener { bar, minValue, maxValue ->

            this.minValue = String.format("%.3f", minValue).toDouble()
            this.maxValue = String.format("%.3f", maxValue).toDouble()
        }
        seekBar.isNotifyWhileDragging = true

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun filterProducts() {
        var filterProducts: ArrayList<Products> = arrayListOf()

        if (checkboxText.size == 0) {
            checkboxText.add(checkboxT_Shirt.text.toString())
            checkboxText.add(checkboxAccessories.text.toString())
            checkboxText.add(checkboxShoes.text.toString())
        }

        for (index in 0..this.products.size - 1) {
            if ((products[index].variants[0].price?.toDouble()!! >= this.minValue)
                && (this.maxValue >= this.products[index].variants[0].price!!.toDouble()!!)
                && (checkboxText.contains(products[index].productType))

            ) {
                filterProducts.add(products[index])
            }
        }
        if (filterProducts.size > 0) {
            categoryAdapter.setData(filterProducts, categoryVM.currencySymbol, categoryVM.currencyValue)
        } else {
            categoryAdapter.setData(this.products, categoryVM.currencySymbol, categoryVM.currencyValue)
        }
    }
}