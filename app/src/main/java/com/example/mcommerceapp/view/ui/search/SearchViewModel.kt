package com.example.mcommerceapp.view.ui.search

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

class SearchViewModel(var iProducts : ProductsRepo) : ViewModel(){


    private val _category: MutableLiveData<HashSet<ProductFields>> = ProductRepo.subCollections
    var  category: LiveData<HashSet<ProductFields>> = _category

    private val _smartCollection : MutableLiveData<HashSet<SmartCollections>> = ProductRepo.vendors
    var  smartCollection: LiveData<HashSet<SmartCollections>> = _smartCollection

    private val _products = MutableLiveData<ArrayList<Products>>()
    var  products: LiveData<ArrayList<Products>> = _products

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            val products = iProducts.getAllProducts()
            withContext(Dispatchers.Main){
                _products.postValue(products)
            }
        }
    }
}