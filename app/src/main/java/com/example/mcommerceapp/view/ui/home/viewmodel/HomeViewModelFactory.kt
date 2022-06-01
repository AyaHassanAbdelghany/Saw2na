package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.CollectionsRepo

class HomeViewModelFactory (private val iProducts: CollectionsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iProducts) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}
