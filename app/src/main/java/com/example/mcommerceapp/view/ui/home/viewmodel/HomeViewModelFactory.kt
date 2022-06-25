package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.IProductsRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo

class HomeViewModelFactory(
    private val iProducts: ICollectionsRepo,
    var products: IProductsRepo,
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
