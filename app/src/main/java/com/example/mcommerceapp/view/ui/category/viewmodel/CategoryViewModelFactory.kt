package com.example.mcommerceapp.view.ui.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo

class CategoryViewModelFactory(private val iCategory: CategoryRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(iCategory) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }

}