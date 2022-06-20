package com.example.mcommerceapp.view.ui.payment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.orders_repository.IOrderPayment
import com.example.mcommerceapp.model.user_repository.UserRepo

class PaymentViewmodelFactory(
    private val iOrder: IOrderPayment,
    private val addressesRepo: AddressesRepo,
    private val userRepo: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PaymentViewmodel::class.java)) {
            PaymentViewmodel(iOrder, addressesRepo, userRepo) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}