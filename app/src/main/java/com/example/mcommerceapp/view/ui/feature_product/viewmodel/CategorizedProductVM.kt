package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategorizedProductVM (var iCategory : CategoryRepo): ViewModel()
{

    private val _products = MutableLiveData<ArrayList<Products>>()
    var products: LiveData<ArrayList<Products>> = _products


//
//    fun getProductsCollection(productType: String, collectionId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val product = iProducts.getProductCollection(productType, collectionId)
//            withContext(Dispatchers.Main){
//                _products.postValue(product)
//            }
//        }
//    }
    fun getProductsVendor(vendor: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = iCategory.getProductsVendor(vendor)
            withContext(Dispatchers.Main){
                _products.postValue(product)
            }
        }
    }

}