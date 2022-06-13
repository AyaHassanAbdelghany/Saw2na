package com.example.mcommerceapp.view.ui.shopping_cart.view

interface CartCommunicator {
    fun calculateNewSubTotal(value: Double)
    fun deleteProductFromCart(index: Int)
    fun increaseUpdateInList(index: Int)
    fun decreaseUpdateInList(index: Int)
}