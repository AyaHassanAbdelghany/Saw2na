package com.example.mcommerceapp.view.ui.home.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.product.IProducts
import com.example.mcommerceapp.model.shopify_repository.smartcollections.ISmartCollections

import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel

class HomeViewModelFactory (private val iSmartCollections: ISmartCollections, private val iProducts: IProducts) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iSmartCollections!!,iProducts) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}
