package com.example.mcommerceapp.model.shopify_repository

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.ProductResponse
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.products.ProductsFieldsResponse
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections


interface ICustomCollections {
    suspend fun  getCustomCollections(): ArrayList<CustomCollections>
}

