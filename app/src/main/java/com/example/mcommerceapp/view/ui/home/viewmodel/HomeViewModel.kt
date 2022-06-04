package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(var iProducts :CollectionsRepo): ViewModel() {

    private val _vendors : MutableLiveData<HashSet<SmartCollections>> = ProductRepo.vendors
    var  vendors : LiveData<HashSet<SmartCollections>>  = _vendors

    private val _collections :MutableLiveData<HashSet<ProductFields>> = ProductRepo.subCollections
    var  collections : LiveData<HashSet<ProductFields>>  = _collections


    fun getProduct(fields: String) {
        viewModelScope.launch(Dispatchers.IO) {
              iProducts.getSmartCollections()
             iProducts.getSubCollection(fields)
        }
    }
  }