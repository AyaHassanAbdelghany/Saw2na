package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo

class CategoryViewModelFactory(
    private val iCategory: CategoryRepo,
    private var iCurrency: StoredCurrency
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(iCategory, iCurrency) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }

}