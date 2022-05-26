package com.example.mcommerceapp.model.shopify_repository

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.Products

interface IProducts {
    suspend fun getProducts() :ArrayList<Products>
}

interface ICustomCollections {
    suspend fun  getCustomCollections(): ArrayList<CustomCollections>
}