package com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.ProductDetailRepo
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM

class ProductDetailVMFactory (private val iProducts: ProductDetailRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailVM::class.java)) {
            ProductDetailVM(iProducts) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}