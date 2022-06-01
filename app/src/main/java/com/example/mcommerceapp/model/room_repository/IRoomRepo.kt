package com.example.mcommerceapp.model.room_repository

import com.example.mcommerceapp.pojo.favorite_products.FavProducts

interface IFavProductRoomRepo {
    suspend fun getAllFavoriteProducts(): List<FavProducts>
    suspend fun checkForFavoriteProductById(productId: String): Int
    fun insertFavoriteProduct(product: FavProducts)
    fun deleteFavoriteProduct(product: FavProducts)
    fun deleteAllProducts()
}
