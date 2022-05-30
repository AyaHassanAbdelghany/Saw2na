package com.example.mcommerceapp.model.remote_source

import android.util.Log
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.interfaces.IRemoteSource
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.ProductResponse
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.products.ProductsFieldsResponse
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class RemoteSource : IRemoteSource {
    private val gson = Gson()

    private val api : ShopifyService =
        ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)


    override suspend fun  getAllProducts(): ArrayList<Products>{
        val res = api.get(Keys.PRODUCTS)
    return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun  getProductsTypes(fields:String): ArrayList<ProductFields>{
        val res = api.getQuery(Keys.PRODUCTS,fields)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<ArrayList<ProductFields>>() {}.type
        )
    }
    override suspend fun getSmartCollections(): ArrayList<SmartCollections> {
        val res = api.get(Keys.SMART_COLLECTIONS)
        return gson.fromJson(
            res.body()!!.get("smart_collections") as JsonArray,
            object : TypeToken<ArrayList<SmartCollections>>() {}.type
        )    }


    private inline fun <reified T>parsingJsonToObject(jsonObject: JsonArray) : T {
        val gson = Gson()
        var x = gson.fromJson( jsonObject , T::class.java)
        return x
    }
}