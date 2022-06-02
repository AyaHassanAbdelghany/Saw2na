package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductTypeRepo
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel

class CategorizedProductVMFactory (private val iProducts: ProductTypeRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategorizedProductVM::class.java)) {
            CategorizedProductVM(iProducts) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}