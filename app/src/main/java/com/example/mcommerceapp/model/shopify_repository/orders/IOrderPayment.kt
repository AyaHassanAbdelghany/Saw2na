package com.example.mcommerceapp.model.shopify_repository.orders

import orders.Order

interface IOrderPayment {
    suspend fun createOrder(order: Order): Order
}
