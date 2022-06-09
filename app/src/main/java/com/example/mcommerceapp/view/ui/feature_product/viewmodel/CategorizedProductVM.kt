package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategorizedProductVM (private var iCategory : CategoryRepo, private var iCurrency: StoredCurrency): ViewModel()
{

    private val _products = MutableLiveData<ArrayList<Products>>()
    var products: LiveData<ArrayList<Products>> = _products


    private val _allProducts: MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var allProducts: LiveData<ArrayList<Products>> = _allProducts


    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    fun getProductsVendor(vendor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = iCategory.getProductsVendor(vendor)
            withContext(Dispatchers.Main){
                _products.postValue(product)
            }
        }
    }

}