package com.example.mcommerceapp.model.shopify_repository.product

import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface IProducts {
    suspend fun getProducts() :ArrayList<Products>
    suspend fun  getProductsTypes(fields:String): ArrayList<ProductFields>
    suspend fun  getSmartCollections(): ArrayList<SmartCollections>
}