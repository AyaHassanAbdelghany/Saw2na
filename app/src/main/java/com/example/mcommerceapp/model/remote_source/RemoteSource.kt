package com.example.mcommerceapp.model.remote_source

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.interfaces.IRemoteSource
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.Products
import com.google.gson.Gson
import com.google.gson.JsonArray

class RemoteSource : IRemoteSource {
    private val api : ShopifyService =
        ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)


    override suspend fun getCustomCollections(): ArrayList<CustomCollections> {
        val res = api.get(Keys.CUSTOM_COLLECTIONS)

        return parsingJsonToObject(res.body()!!.get("custom_collections").asJsonArray)
    }

    override suspend fun getAllProducts(): ArrayList<Products> {

        val res = api.get(Keys.PRODUCTS)

        return parsingJsonToObject(res.body()!!.get("products").asJsonArray)
    }








    private inline fun <reified T>parsingJsonToObject(jsonObject: JsonArray) : T {
        val gson = Gson()

        var x = gson.fromJson( jsonObject , T::class.java)

        return x
    }


}