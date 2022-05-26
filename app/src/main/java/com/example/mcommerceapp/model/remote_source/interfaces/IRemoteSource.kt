package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.Products

interface IRemoteSource {
    suspend fun getAllProducts() : ArrayList<Products>

    suspend fun getCustomCollections() : ArrayList<CustomCollections>
}