package com.example.mcommerceapp.model.orders_repository

import orders.Order

interface IOrderPayment {
    suspend fun createOrder(order: Order): Order
}
