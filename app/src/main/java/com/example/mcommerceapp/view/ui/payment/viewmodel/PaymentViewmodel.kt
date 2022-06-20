package com.example.mcommerceapp.view.ui.payment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.orders_repository.IOrderPayment
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.pojo.orders.DiscountCodes
import com.example.mcommerceapp.pojo.orders.ShippingAddress
import com.example.mcommerceapp.pojo.user.User
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import orders.LineItems
import orders.Order

class PaymentViewmodel(
    private val iOrder: IOrderPayment,
    private val addressesRepo: AddressesRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val _addresses = MutableLiveData<Addresses>()
    val addresses: LiveData<Addresses> = _addresses

    fun getUser(): LiveData<User> {
        return userRepo.retrieveUserFromFireStore()
    }

    fun getAddress(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = addressesRepo.getAddressByCustomerID(user.userID)
            withContext(Dispatchers.Main) {
                addresses.forEach {
                    if (it.default!!) {
                        _addresses.postValue(it)
                    }
                }
            }
        }
    }

    fun createOrder(
        draftOrders: ArrayList<DraftOrder>,
        shippingAddress: ShippingAddress,
        user: User,
        discountCodes: DiscountCodes
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val order = makeOrder(draftOrders, shippingAddress, user, discountCodes)
            iOrder.createOrder(order)
        }
    }

    private fun makeOrder(
        draftOrders: ArrayList<DraftOrder>,
        shippingAddress: ShippingAddress,
        user: User,
        discountCodes: DiscountCodes
    ): Order {
        var orders = Order()
        orders.shippingAddress = shippingAddress
        orders.email = user.email
        orders.discountCodes.add(
            DiscountCodes(
                code = discountCodes.code,
                amount = discountCodes.amount,
                type = discountCodes.type
            )
        )
        for (draftOrder in draftOrders) {
            orders.lineItems.add(
                LineItems(
                    quantity = draftOrder.lineItems[0].quantity,
                    variantId = draftOrder.lineItems[0].variantId
                )
            )
        }

        return orders
    }
}