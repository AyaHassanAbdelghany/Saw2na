package com.example.mcommerceapp.model.remote_source.customer

import android.util.Log
import com.example.example.Customers
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


    suspend fun createCustomer(req: RequestBody): String {
        val res = api.createCustomer(req)
        Log.i("createCustomer", "createCustomer: $res")
        return if (res.message() == "Created") {
            (res.body()!!.get("customer") as JsonObject).get("id").toString()
        } else {
            "null"
        }
    }

    suspend fun getCustomerById(id: String): Customers {

        val res = api.getCustomerByID(id)
        Log.i("getCustomerById", "res : $res")
        return gson.fromJson(
            res.body()!!.get("customer") as JsonObject,
            object : TypeToken<Customers>() {}.type
        )

    }

    suspend fun updateCustomer(userID: String, req: RequestBody): Customers {
        val res = api.updateCustomerByID(userID, req)
        Log.i("updateCustomer", "updateCustomer: $res")
        return gson.fromJson(
            res.body()!!.get("customer") as JsonObject,
            object : TypeToken<Customers>() {}.type
        )
    }
}