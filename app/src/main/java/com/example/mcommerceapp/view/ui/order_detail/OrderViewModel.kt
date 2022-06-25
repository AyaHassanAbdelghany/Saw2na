package com.example.mcommerceapp.view.ui.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import orders.Order

class OrderDetailViewModel(
    private val iOrders: OrdersRepo, iCurrency: StoredCurrency
) : ViewModel() {

    private val _orders = MutableLiveData<Order>()
    var orders: LiveData<Order> = _orders

    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    fun getOrder(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val orders = iOrders.getOrderByID(id)
            withContext(Dispatchers.Main) {
                _orders.postValue(orders)
            }
        }
    }

}