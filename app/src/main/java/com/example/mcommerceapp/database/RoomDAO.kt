package com.example.mcommerceapp.database

import androidx.room.*
import com.example.mcommerceapp.pojo.favorite_products.FavProducts

@Dao
interface RoomDAO {
    @Query("SELECT * FROM favorite_products")
    suspend fun getAllFavoriteProducts(): List<FavProducts>

    @Query("SELECT * FROM favorite_products WHERE ID LIKE :productId " + "LIMIT 1")
    suspend fun checkForFavoriteProductById(productId: String): FavProducts

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteProduct(product: FavProducts)

    @Delete
    fun deleteFavoriteProduct(product: FavProducts)

    @Query("Delete FROM favorite_products")
    fun deleteAllProducts()
}