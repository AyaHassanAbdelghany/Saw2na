package com.example.mcommerceapp.view.ui.home.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.IProducts
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel

class HomeViewModelFactory (private val iProduct :IProducts) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(iProduct!!) as T
        } else {
            throw IllegalArgumentException("Error")
        }    }
}
