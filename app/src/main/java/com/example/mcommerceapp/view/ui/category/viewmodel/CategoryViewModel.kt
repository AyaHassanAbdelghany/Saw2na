package com.example.mcommerceapp.view.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(var iProducts :CategoryRepo) : ViewModel() {

    private val _category = MutableLiveData<HashSet<ProductFields>>()
    var  category: LiveData<HashSet<ProductFields>>  = _category

    private val _customCollection : MutableLiveData<ArrayList<CustomCollections>> = ProductRepo.customCollection
    var  customCollection: LiveData<ArrayList<CustomCollections>>  = _customCollection

    fun getCollectionId(title :String){
        viewModelScope.launch(Dispatchers.IO) {
            iProducts.getCollectionId(title)

        }
    }
    fun getCategoryForVendor(fields :String,collectionId :String,vendor:String){
        viewModelScope.launch(Dispatchers.IO) {
            val category = iProducts.getCategoryForVendor(fields,collectionId,vendor)
            withContext(Dispatchers.Main){
                _category.postValue(category)
            }
        }
    }

    fun getCategoryForCollection(fields :String,collectionId :String){
        viewModelScope.launch(Dispatchers.IO) {
            val categoryForVendor = iProducts.getCategoryForCollection(fields,collectionId)
            withContext(Dispatchers.Main){
                _category.postValue(categoryForVendor)
            }
        }
    }
}