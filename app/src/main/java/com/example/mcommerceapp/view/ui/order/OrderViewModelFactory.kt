package com.example.mcommerceapp.view.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.user_repository.UserRepo

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