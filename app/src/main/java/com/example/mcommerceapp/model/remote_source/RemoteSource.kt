package com.example.mcommerceapp.model.remote_source

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.interfaces.ICurrencyRemoteSource
import com.example.mcommerceapp.model.remote_source.interfaces.IRemoteSource
import com.example.mcommerceapp.network.CurrencyService
import com.example.mcommerceapp.network.ICurrencyService
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService
import com.example.mcommerceapp.pojo.currency.CurrencyConversion
import com.example.mcommerceapp.pojo.currency.CurrencySymbols
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class RemoteSource : IRemoteSource, ICurrencyRemoteSource {
    private val gson = Gson()

    private val api: ShopifyService =
        ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)

    private val currencyApi: ICurrencyService =
        CurrencyService.getInstance().create(ICurrencyService::class.java)


    override suspend fun getAllProducts(): ArrayList<Products> {
        val res = api.get(Keys.PRODUCTS)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun getProductsTypes(fields: String): ArrayList<ProductFields> {
        val res = api.getQuery(Keys.PRODUCTS, fields)
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
        )
    }


    private inline fun <reified T> parsingJsonToObject(jsonObject: JsonArray): T {
        val gson = Gson()
        var x = gson.fromJson(jsonObject, T::class.java)
        return x
    }


    override suspend fun getCurrencySymbols(): CurrencySymbols {
        val res = currencyApi.getAllCurrencySymbols()
        return gson.fromJson(
            res.body()!!,
            object : TypeToken<CurrencySymbols>() {}.type
        )
    }

    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): CurrencyConversion {
        val res = currencyApi.convertCurrency(from, to, amount)
        return gson.fromJson(
            res.body()!!,
            object : TypeToken<CurrencyConversion>() {}.type
        )
    }
}