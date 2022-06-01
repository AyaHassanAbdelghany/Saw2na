package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface IRemoteSource {
    suspend fun getAllProducts() : ArrayList<Products>
    suspend fun getSmartCollections() : ArrayList<SmartCollections>

    suspend fun getCustomCollections() : ArrayList<CustomCollections>
    suspend fun getCategoryForCollection(fields:String,collectionId:String): HashSet<ProductFields>
    suspend fun getCategoryForVendor(fields:String,collectionId :String,vendor:String): HashSet<ProductFields>
    suspend fun getSubCollections(fields: String): HashSet<ProductFields>
    suspend fun getProductDetail(id: Long): Products

   
    suspend fun getProductsTypes(fields: String): ArrayList<ProductFields>
}