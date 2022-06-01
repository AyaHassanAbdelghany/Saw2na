package com.example.mcommerceapp.model.room_repository

import android.content.Context
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.pojo.favorite_products.FavProducts

class RoomRepo private constructor(
    private var localSource: LocalSource,
    private var myContext: Context
) :
    IFavProductRoomRepo {

    companion object {
        private var roomRepo: RoomRepo? = null

        fun getInstance(localSource: LocalSource, myContext: Context): RoomRepo {
            if (roomRepo == null) {
                roomRepo = RoomRepo(localSource, myContext)
            }
            return roomRepo!!
        }
    }

    override suspend fun getAllFavoriteProducts(): List<FavProducts> {
        return localSource.getAllFavoriteProducts()
    }

    override suspend fun checkForFavoriteProductById(productId: String): Int {
        return localSource.checkForFavoriteProductById(productId)
    }

    override fun insertFavoriteProduct(product: FavProducts) {
        localSource.insertFavoriteProduct(product)
    }

    override fun deleteFavoriteProduct(product: FavProducts) {
        localSource.deleteFavoriteProduct(product)
    }

    override fun deleteAllProducts() {
        localSource.deleteAllProducts()
    }
}