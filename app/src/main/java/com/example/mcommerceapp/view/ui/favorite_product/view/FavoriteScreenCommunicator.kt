package com.example.mcommerceapp.view.ui.favorite_product.view

import com.example.mcommerceapp.pojo.favorite_products.FavProducts

interface FavoriteScreenCommunicator {
    fun performDeleteProduct(product: FavProducts)
}