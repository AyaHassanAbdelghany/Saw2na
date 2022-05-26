package com.example.mcommerceapp.model

import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService
import com.example.mcommerceapp.pojo.products.ProductResponse

class ShopifyRepo private constructor() : ProductsInterface {

    override suspend fun getProducts(): ProductResponse {
        val s: ShopifyService =
            ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)
        val res = s.getProducts("products.json")
        return res!!
    }
}