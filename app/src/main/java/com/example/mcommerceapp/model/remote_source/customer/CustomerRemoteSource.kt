package com.example.mcommerceapp.model.remote_source.customer

import com.example.example.Customers
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.product.ShopifyService
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody

class CustomerRemoteSource {

    private val api: ShopifyService =
        ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)
    private val gson = Gson()


    suspend fun createCustomer(req:RequestBody):String {
        val res = api.createCustomer(req)
        return if (res.message() == "Created"){
            (res.body()!!.get(Keys.CUSTOMER) as JsonObject).get("id").toString()
        }else{
            "null"
        }
    }

    suspend fun getCustomerById(id : String):Customers{

        val res = api.getCustomerByID(id)
        return gson.fromJson(
            res.body()!!.get(Keys.CUSTOMER) as JsonObject,
            object : TypeToken<Customers>() {}.type
        )

    }

    suspend fun updateCustomer(userID:String ,req:RequestBody):Customers {
        val res = api.updateCustomerByID(userID,req)
        return gson.fromJson(
            res.body()!!.get(Keys.CUSTOMER) as JsonObject,
            object : TypeToken<Customers>() {}.type
        )
    }
}