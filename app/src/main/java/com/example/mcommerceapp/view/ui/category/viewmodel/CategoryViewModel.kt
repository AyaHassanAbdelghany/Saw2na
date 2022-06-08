package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(var iCategory :CategoryRepo) : ViewModel() {

    private val _category = MutableLiveData<HashSet<ProductFields>>()
    var  category: LiveData<HashSet<ProductFields>>  = _category

    private val _customCollection : MutableLiveData<ArrayList<CustomCollections>> = ProductRepo.customCollection
    var  customCollection: LiveData<ArrayList<CustomCollections>>  = _customCollection

    private val _collectionProducts = MutableLiveData<ArrayList<Products>>()
    var  collectionProducts: LiveData<ArrayList<Products>>  = _collectionProducts

    private val _allProducts : MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var  allProducts: LiveData<ArrayList<Products>>  = _allProducts

    fun getCollectionId(title :String){
        viewModelScope.launch(Dispatchers.IO) {
            iCategory.getCollectionId(title)

        }
    }

    fun getCollectionProducts(collectionId :String){
        viewModelScope.launch(Dispatchers.IO) {
            val collectionProducts = iCategory.getCollectionProducts(collectionId)
            withContext(Dispatchers.Main){
                _collectionProducts.postValue(collectionProducts)
            }
        }
    }
    fun getCategoryForVendor(fields :String,collectionId :String,vendor:String){
        viewModelScope.launch(Dispatchers.IO) {
            val category = iCategory.getCategoryForVendor(fields,collectionId,vendor)
            withContext(Dispatchers.Main){
                _category.postValue(category)
            }
        }
    }


    fun getCategoryForCollection(fields :String,collectionId :String){
        viewModelScope.launch(Dispatchers.IO) {
            val categoryForVendor = iCategory.getCategoryForCollection(fields,collectionId)
            withContext(Dispatchers.Main){
                _category.postValue(categoryForVendor)
            }
        }
    }
}