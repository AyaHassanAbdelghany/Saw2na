package com.example.mcommerceapp.model

interface ProductsInterface {
    suspend fun <T> getProducts(): T
}