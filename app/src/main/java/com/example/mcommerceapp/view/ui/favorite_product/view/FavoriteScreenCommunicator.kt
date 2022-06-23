package com.example.mcommerceapp.view.ui.favorite_product.view

import draft_orders.DraftOrder

interface FavoriteScreenCommunicator {
    fun performDeleteProduct(product: DraftOrder)
}