package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICategoryRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo

class CategoryViewModelFactory(
    private val iCategory: ICategoryRepo,
    private var iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(iCategory, iCurrency, iUser) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }

}