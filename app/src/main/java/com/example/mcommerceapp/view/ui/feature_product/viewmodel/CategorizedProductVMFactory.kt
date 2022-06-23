package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.user_repository.UserRepo

class CategorizedProductVMFactory(
    private val iCategory: CategoryRepo,
    private val iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategorizedProductVM::class.java)) {
            CategorizedProductVM(iCategory, iCurrency, iUser) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}