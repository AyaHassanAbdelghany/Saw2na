package com.example.mcommerceapp.view.ui.product_detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.product.ProductDetailRepo
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailVM(private val iProducts: ProductDetailRepo) : ViewModel() {



    private val _productDetail = MutableLiveData<Products>()
    var productDetail: LiveData<Products> = _productDetail

    fun getProductDetail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = iProducts.getProductDetail(id)
            Log.d("Detail", detail.toString())

            withContext(Dispatchers.Main) {
                _productDetail.postValue(detail)
            }
        }
    }
}