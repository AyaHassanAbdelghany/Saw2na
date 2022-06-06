package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.ProductTypeRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategorizedProductVM (var iProducts : ProductTypeRepo): ViewModel()
{

    private val _products = MutableLiveData<ArrayList<Products>>()
    var products: LiveData<ArrayList<Products>> = _products



    fun getProductCollection(productType: String, collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = iProducts.getProductCollection(productType, collectionId)
            withContext(Dispatchers.Main){
                _products.postValue(product)
            }
        }
    }
    fun getProductVendor(productType: String, vendor: String, collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = iProducts.getProductVendor(productType, vendor, collectionId)
            withContext(Dispatchers.Main){
                _products.postValue(product)
            }
        }
    }

}