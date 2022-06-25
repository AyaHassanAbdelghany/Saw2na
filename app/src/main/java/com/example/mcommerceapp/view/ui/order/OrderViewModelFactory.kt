package com.example.mcommerceapp.view.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo

class OrderViewModelFactory(
    private val iOrders: OrdersRepo,
    private val iUser: UserRepo,
    private val iCurrency: StoredCurrency
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            OrderViewModel(iOrders, iUser, iCurrency) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}