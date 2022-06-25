package com.example.mcommerceapp.view.ui.feature_product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICategoryRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo

class CategorizedProductVMFactory(
    private val iCategory: ICategoryRepo,
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