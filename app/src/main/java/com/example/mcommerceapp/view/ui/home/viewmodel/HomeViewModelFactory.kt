package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductsRepo
import com.example.mcommerceapp.model.user_repository.UserRepo

class HomeViewModelFactory(
    private val iProducts: CollectionsRepo,
    var products: ProductsRepo,
    private var iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iProducts, products, iCurrency, iUser) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}
