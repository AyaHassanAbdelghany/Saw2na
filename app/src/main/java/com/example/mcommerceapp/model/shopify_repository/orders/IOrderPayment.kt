package com.example.mcommerceapp.model.shopify_repository.orders

import com.example.mcommerceapp.pojo.products.Variants
import orders.Order

interface IOrderPayment {
    suspend fun createOrder(order: Order): Order
    suspend fun adjustInventoryItem(inventoryID:Long,amount:Int)
    suspend fun getVariantByID(variantID:String): Variants
}
