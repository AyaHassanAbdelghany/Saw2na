package com.example.mcommerceapp.view.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import orders.Order

class OrderViewModel(
    private val iOrders: OrdersRepo,
    private val iUser: UserRepo
) : ViewModel() {

    private val _orders = MutableLiveData<ArrayList<Order>>()
    var orders: LiveData<ArrayList<Order>> = _orders

    private val _user = MutableLiveData<ArrayList<Order>>()
    var user: LiveData<ArrayList<Order>> = _user

    fun getAllOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = iUser.getUser()
            Log.d("iiiiiiiiiiid", user.toString())
            val orders = iOrders.getAllOrders(user.userID)
            withContext(Dispatchers.Main) {
                _orders.postValue(orders)
            }
        }
    }

}