package com.example.mcommerceapp.view.ui.product_detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.IProducts
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailVM(iProducts: IProducts) : ViewModel() {

    private val _iProducts: IProducts = iProducts


    private val _productDetail = MutableLiveData<Products>()
    var productDetail: LiveData<Products> = _productDetail

    fun getProductDetail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = _iProducts.getProductDetail(id)
            Log.d("Detail", detail.toString())

            withContext(Dispatchers.Main) {
                _productDetail.postValue(detail)
            }
        }
    }
}