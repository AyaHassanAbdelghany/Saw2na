package com.example.mcommerceapp.view.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CategoryRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductsRepo

class SearchViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel() as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}