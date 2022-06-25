package com.example.mcommerceapp.view.ui.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo

class OrderDetailViewModelFactory(
    private val iOrders: OrdersRepo, private val iCurrency: StoredCurrency
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            OrderDetailViewModel(iOrders, iCurrency) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}