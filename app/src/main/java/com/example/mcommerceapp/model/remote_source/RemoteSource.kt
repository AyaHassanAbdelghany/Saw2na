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
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

class RemoteSource : IRemoteSource, ICurrencyRemoteSource {
    private val gson = Gson()

    private val api: ShopifyService =
        ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)

    private val currencyApi: ICurrencyService =
        CurrencyService.getInstance().create(ICurrencyService::class.java)

    override suspend fun getProductCollection(productType: String, collectionId: String): ArrayList<Products> {
        val res = api.getProductCollection(Keys.PRODUCTS, productType, collectionId)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun getProductVendor(productType: String, vendor: String, collectionId: String): ArrayList<Products> {
        val res = api.getProductVendor(Keys.PRODUCTS, productType, vendor, collectionId)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }


    override suspend fun getCategoryForCollection(fields: String,collectionId:String): HashSet<ProductFields> {
        val res = api.getCategoryForCollection(Keys.PRODUCTS, fields,collectionId)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<HashSet<ProductFields>>() {}.type
        )
    }

    override suspend fun getCategoryForVendor(
        fields: String,
        collectionId: String,
        vendor: String
    ): HashSet<ProductFields> {
        val res = api.getCategoryForVendor(Keys.PRODUCTS, fields, collectionId, vendor)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<HashSet<ProductFields>>() {}.type
        )
    }

    override suspend fun getProductsTypes(fields: String): ArrayList<ProductFields> {
        val res = api.getQuery(Keys.PRODUCTS, fields)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<HashSet<ProductFields>>() {}.type
        )
    }

    override suspend fun getSmartCollections(): HashSet<SmartCollections> {
        val res = api.get(Keys.SMART_COLLECTIONS)
        return gson.fromJson(
            res.body()!!.get("smart_collections") as JsonArray,
            object : TypeToken<HashSet<SmartCollections>>() {}.type
        )
    }

    override suspend fun getCustomCollections(): ArrayList<CustomCollections> {
        val res = api.get(Keys.CUSTOM_COLLECTIONS)
        return gson.fromJson(
            res.body()!!.get("custom_collections") as JsonArray,
            object : TypeToken<ArrayList<CustomCollections>>() {}.type
        )    }

    override suspend fun getCollectionId(title: String): ArrayList<CustomCollections> {
        val res = api.getCollectionId(Keys.CUSTOM_COLLECTIONS,title)
        return gson.fromJson(
            res.body()!!.get("custom_collections") as JsonArray,
            object : TypeToken<ArrayList<CustomCollections>>() {}.type
        )
    }

    override suspend fun getSubCollections(fields: String): HashSet<ProductFields> {
        val res = api.getSubCollection(Keys.PRODUCTS, fields)
        return gson.fromJson(
            res.body()!!.get("products") as JsonArray,
            object : TypeToken<HashSet<ProductFields>>() {}.type
        )    }

    override suspend fun getProductDetail(id: String): Products{
        val res = api.get("products${"/$id"}.json")
        return gson.fromJson(
            res.body()!!.get("product") as JsonObject,
            object : TypeToken<Products>() {}.type
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