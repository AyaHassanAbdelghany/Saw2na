package com.example.mcommerceapp.model.remote_source.products

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.currency.ICurrencyService
import com.example.mcommerceapp.network.products.ProductService
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

class ProductRemoteSource  private constructor(): IProductRemoteSource, ICurrencyRemoteSource {
    private val gson = Gson()

    private val api: ProductService =
        ShopifyRetrofitHelper.getInstance().create(ProductService::class.java)

    private val currencyApi: ICurrencyService =
        ShopifyRetrofitHelper.getInstance().create(ICurrencyService::class.java)

    companion object {
        private var remoteSource: ProductRemoteSource? = null
        fun getInstance(): ProductRemoteSource {
            return remoteSource ?: ProductRemoteSource()
        }
    }

    override suspend fun getAllProducts(): ArrayList<Products> {
        val res = api.getAllProducts(Keys.PRODUCTS_URL)
        return gson.fromJson(
            res.body()!!.get(Keys.PRODUCTS) as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun getProductsCollection(collectionId: String): ArrayList<Products> {
        val res = api.getProductsCollection(Keys.PRODUCTS_URL, collectionId)
        return gson.fromJson(
            res.body()!!.get(Keys.PRODUCTS) as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun getProductsVendor(vendor: String): ArrayList<Products> {
        val res = api.getProductsVendor(Keys.PRODUCTS_URL, vendor)
        return gson.fromJson(
            res.body()!!.get(Keys.PRODUCTS) as JsonArray,
            object : TypeToken<ArrayList<Products>>() {}.type
        )
    }

    override suspend fun getSmartCollections(): HashSet<SmartCollections> {
        val res = api.getAllProducts(Keys.SMART_COLLECTIONS_URL)
        return gson.fromJson(
            res.body()!!.get(Keys.SMART_COLLECTIONS) as JsonArray,
            object : TypeToken<HashSet<SmartCollections>>() {}.type
        )
    }

    override suspend fun getSubCollections(fields: String): HashSet<ProductFields> {
        val res = api.getSubCollection(Keys.PRODUCTS_URL, fields)
        return gson.fromJson(
            res.body()!!.get(Keys.PRODUCTS) as JsonArray,
            object : TypeToken<HashSet<ProductFields>>() {}.type
        )
    }

    override suspend fun getCollectionId(id: String): ArrayList<CustomCollections> {
        val res = api.getCollectionId(Keys.CUSTOM_COLLECTIONS_URL, id)
        return gson.fromJson(
            res.body()!!.get(Keys.CUSTOM_COLLECTIONS) as JsonArray,
            object : TypeToken<ArrayList<CustomCollections>>() {}.type
        )
    }

    override suspend fun getProductDetail(id: String): Products {
        val res = api.getAllProducts("${Keys.PRODUCTS}/${id}${Keys.JSON_Extension}")
        return gson.fromJson(
            res.body()!!.get(Keys.PRODUCT) as JsonObject,
            object : TypeToken<Products>() {}.type
        )
    }


    private inline fun <reified T> parsingJsonToObject(jsonObject: JsonArray): T {
        val gson = Gson()
        val x = gson.fromJson(jsonObject, T::class.java)
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