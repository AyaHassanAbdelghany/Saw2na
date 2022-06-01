package com.example.mcommerceapp.model.local_source

import android.content.Context
import com.example.mcommerceapp.database.RoomDAO
import com.example.mcommerceapp.database.RoomDb
import com.example.mcommerceapp.pojo.favorite_products.FavProducts

class LocalSource(context: Context){
    private val favProductsDAO: RoomDAO

    init {
        val db: RoomDb = RoomDb.getInstance(context)
        favProductsDAO = db.roomDAO()
    }


    companion object {
        private var instance: LocalSource? = null
        fun getInstance(context: Context): LocalSource {
            return instance ?: LocalSource(context)
        }
    }

    suspend fun getAllFavoriteProducts(): List<FavProducts> {
        return favProductsDAO.getAllFavoriteProducts()
    }

    suspend fun checkForFavoriteProductById(productId: String): Int {
        if (favProductsDAO.checkForFavoriteProductById(productId) != null) {
            return 1
        } else {
            return 0
        }
    }

    fun insertFavoriteProduct(product: FavProducts) {
        favProductsDAO.insertFavoriteProduct(product)
    }

    fun deleteFavoriteProduct(product: FavProducts) {
        favProductsDAO.deleteFavoriteProduct(product)
    }

    fun deleteAllProducts() {
        favProductsDAO.deleteAllProducts()
    }
}