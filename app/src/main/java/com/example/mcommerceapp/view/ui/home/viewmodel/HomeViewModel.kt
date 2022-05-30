package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.IProducts
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(iProducts :IProducts): ViewModel() {

    private val _iProducts :IProducts = iProducts
    private val _vendors = MutableLiveData<MutableMap<String,String>>()
    var  vendors : LiveData<MutableMap<String,String>>  = _vendors
    private val _catogeries = MutableLiveData<MutableSet<String>>()
    var  catogeries : LiveData<MutableSet<String>>  = _catogeries

    private val productsVendors :MutableMap<String,String> = mutableMapOf()
    private val productsCategories :MutableSet<String> = mutableSetOf()


    fun getProduct(fields:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vendors = _iProducts.getSmartCollections()
            val category = _iProducts.getProductsTypes(fields)
            getVendors(vendors)
            getCategories(category)
            withContext(Dispatchers.Main){
                _vendors.postValue(productsVendors)
                _catogeries.postValue(productsCategories)
            }
        }
    }

    fun getVendors(vendors: ArrayList<SmartCollections>){
        for(vendor in vendors){
            vendor.let { productsVendors.put(vendor.title!!, vendor.image?.src!!) }
        }
    }

    fun getCategories(categories: ArrayList<ProductFields>){
        for(category in categories){
            category.productType.let { productsCategories.add(it!!) }
        }
    }
}