package com.example.mcommerceapp.model.shopify_repository.smartcollections

import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface ISmartCollections {
    suspend fun  getSmartCollections(): ArrayList<SmartCollections>
}