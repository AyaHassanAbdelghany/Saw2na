package com.example.mcommerceapp.view.ui.addresses.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.addresses.AddressesRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.view.ui.addresses.view_model.AddressesViewModel

class AddressesViewModelFactory(
    private val customerRepo: AddressesRepo,
    private val userRepo: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddressesViewModel::class.java)) {
            AddressesViewModel(customerRepo, userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}