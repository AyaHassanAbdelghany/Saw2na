package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface IRemoteSource {

    suspend fun getSmartCollections() : HashSet<SmartCollections>
    suspend fun getAllProducts() : ArrayList<Products>
    suspend fun getProductsCollection(collectionId :String) :ArrayList<Products>
    suspend fun  getProductsVendor(vendor:String) :ArrayList<Products>
    suspend fun getCollectionId(title:String) :ArrayList<CustomCollections>
    suspend fun getSubCollections(fields: String): HashSet<ProductFields>


    suspend fun getProductDetail(id: String): Products

}