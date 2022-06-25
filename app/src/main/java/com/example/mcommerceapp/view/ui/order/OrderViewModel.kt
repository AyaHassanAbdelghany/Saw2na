package com.example.mcommerceapp.view.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import orders.Order

class OrderViewModel(
    private val iOrders: OrdersRepo,
    private val iUser: UserRepo,
    private val iCurrency: StoredCurrency
) : ViewModel() {

    private val _orders = MutableLiveData<ArrayList<Order>>()
    var orders: LiveData<ArrayList<Order>> = _orders

    private val _user = MutableLiveData<ArrayList<Order>>()
    var user: LiveData<ArrayList<Order>> = _user

    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    fun getAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = iUser.getUser()
            val orders = iOrders.getAllOrders(user.userID)
            withContext(Dispatchers.Main) {
                _orders.postValue(orders)
            }
        }
    }

}