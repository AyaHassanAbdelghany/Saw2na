package com.example.mcommerceapp.model

import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService

class ShopifyRepo private constructor() : ProductsInterface {

    override suspend fun <T> getProducts(): T {
        val s: ShopifyService =
            ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)
        var res = s.getProducts<T>("products.json")
        return res.body()!!
    }
}