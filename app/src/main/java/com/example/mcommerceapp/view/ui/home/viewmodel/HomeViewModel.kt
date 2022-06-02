package com.example.mcommerceapp.view.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(var iProducts :CollectionsRepo): ViewModel() {

    private val _vendors = MutableLiveData<MutableMap<String,SmartCollections>>()
    var  vendors : LiveData<MutableMap<String,SmartCollections>>  = _vendors

    private val _collections = MutableLiveData<MutableList<String>>()
    var  collections : LiveData<MutableList<String>>  = _collections

    private val productsVendors :MutableMap<String,SmartCollections> = mutableMapOf()
    private val productsCollections :MutableList<String> = mutableListOf()


    fun getProduct(fields: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vendors = iProducts.getSmartCollections()
            val collections = iProducts.getSubCollection(fields)
            Log.e("TYPE", collections.toString())
            getVendors(vendors)
            getCollections(collections)
            withContext(Dispatchers.Main){
                _vendors.postValue(productsVendors)
                _collections.postValue(productsCollections)
            }
        }
    }

    private fun getVendors(vendors: ArrayList<SmartCollections>){
        for(vendor in vendors){
            vendor.let { productsVendors.put(
                vendor.id!!,
                SmartCollections(title = vendor.title,image = vendor.image))
             }

        }
    }

    private fun getCollections(collections: HashSet<ProductFields>){
        productsCollections.clear()
        for(collection in collections){
            collection.let { productsCollections.add(collection.productType) }

        }
    }
}