package com.example.mcommerceapp.pojo.favorite_products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavProducts(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val productId: String,
    @ColumnInfo(name = "Image")
    val productImage: String,
    @ColumnInfo(name = "Price")
    val productPrice: Double,
    @ColumnInfo(name = "Name")
    val productName: String
)
