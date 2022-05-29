package com.example.mcommerceapp.view.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.IProducts
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel (iProduct : IProducts): ViewModel() {

    private val _iProduct :IProducts = iProduct

    private val _vendors = MutableLiveData<MutableList<String>>()
    private val _catogeries = MutableLiveData<MutableList<String>>()
    var  catogeries : LiveData<MutableList<String>>  = _catogeries
    var  vendors : LiveData<MutableList<String>>  = _vendors

    private val productsVendors :MutableList<String> = mutableListOf()
    private val productsCategories :MutableList<String> = mutableListOf()



    fun getProduct() {
        viewModelScope.launch {
           val product = _iProduct.getProducts()
            Log.d("res",product.toString())
            getProductsVendors(product)
            getProductsCategories(product)
            withContext(Dispatchers.Main){
                _vendors.postValue(productsVendors)
                _catogeries.postValue(productsCategories)
            }
        }
    }

    fun getProductsVendors(products: ArrayList<Products>){
        for(product in products){
            product.vendor?.let { productsVendors.add(it) }
        }
    }

    fun getProductsCategories(products: ArrayList<Products>){
        for(product in products){
            product.productType?.let { productsCategories.add(it) }
        }
    }
}