package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo

class CategoryViewModelFactory(private val iProducts: CategoryRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(iProducts) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }

}