package com.example.mcommerceapp.view.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductsRepo
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel() : ViewModel(){


    private val _category: MutableLiveData<HashSet<ProductFields>> = ProductRepo.subCollections
    var  category: LiveData<HashSet<ProductFields>> = _category

    private val _smartCollection : MutableLiveData<HashSet<SmartCollections>> = ProductRepo.vendors
    var  smartCollection: LiveData<HashSet<SmartCollections>> = _smartCollection

    private val _allProducts : MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var  allProducts: LiveData<ArrayList<Products>> = _allProducts

}