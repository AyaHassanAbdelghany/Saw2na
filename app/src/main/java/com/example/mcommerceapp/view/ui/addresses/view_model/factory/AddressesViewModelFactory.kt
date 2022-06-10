package com.example.mcommerceapp.view.ui.addresses.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.view.ui.addresses.view_model.AddressesViewModel

class AddressesViewModelFactory(private val customerRepo: AddressesRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddressesViewModel::class.java)) {
            AddressesViewModel(customerRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}