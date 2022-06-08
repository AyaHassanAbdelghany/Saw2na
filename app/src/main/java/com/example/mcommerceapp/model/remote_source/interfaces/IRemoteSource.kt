package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface IRemoteSource {
    suspend fun getProductCollection(productId: String, collectionId: String): ArrayList<Products>
    suspend fun getSmartCollections() : HashSet<SmartCollections>
    suspend fun getProductVendor(productType: String, vendor: String, collectionId: String): ArrayList<Products>
    suspend fun getCustomCollections() : ArrayList<CustomCollections>
    suspend fun getAllProducts() : ArrayList<Products>
    suspend fun getCollectionProducts(collectionId :String) :ArrayList<Products>

    suspend fun getCollectionId(title:String) :ArrayList<CustomCollections>
    suspend fun getCategoryForCollection(fields:String,collectionId:String): HashSet<ProductFields>
    suspend fun getCategoryForVendor(fields:String,collectionId :String,vendor:String): HashSet<ProductFields>

    suspend fun getSubCollections(fields: String): HashSet<ProductFields>
    suspend fun getProductDetail(id: String): Products

   
    suspend fun getProductsTypes(fields: String): ArrayList<ProductFields>
}