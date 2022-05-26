package com.example.mcommerceapp.model

import com.example.mcommerceapp.pojo.products.ProductResponse

interface ProductsInterface {
    suspend fun getProducts(): ProductResponse
}