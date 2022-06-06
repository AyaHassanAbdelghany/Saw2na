package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductsRepo

class HomeViewModelFactory (private val iProducts: CollectionsRepo, var products: ProductsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iProducts, products) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}
