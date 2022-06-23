package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo

class CategorizedProductVMFactory(
    private val iCategory: CategoryRepo,
    private val iCurrency: StoredCurrency
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategorizedProductVM::class.java)) {
            CategorizedProductVM(iCategory, iCurrency) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}