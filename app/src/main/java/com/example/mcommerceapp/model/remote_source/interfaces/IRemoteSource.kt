package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface IRemoteSource {
    suspend fun getAllProducts(): ArrayList<Products>
    suspend fun getSmartCollections(): ArrayList<SmartCollections>
    suspend fun getProductsTypes(fields: String): ArrayList<ProductFields>
}