package com.example.mcommerceapp.view.ui.shopping_cart.view

interface CartCommunicator {
    fun calculateNewSubTotal()
    fun deleteProductFromCart(index: Int)
    fun increaseUpdateInList(index: Int)
    fun decreaseUpdateInList(index: Int)
    fun onClick(code: String, limit: String)
}