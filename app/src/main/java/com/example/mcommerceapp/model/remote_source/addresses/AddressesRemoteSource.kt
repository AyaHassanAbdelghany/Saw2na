package com.example.mcommerceapp.model.remote_source.addresses

import android.util.Log
import com.example.example.Customers
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.addresses.AddressesService
import com.example.mcommerceapp.pojo.customers.Addresses
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody

class AddressesRemoteSource {

    private val api: AddressesService =
        ShopifyRetrofitHelper.getInstance().create(AddressesService::class.java)
    private val gson = Gson()


    suspend fun addNewAddress(id : String,req:RequestBody): Addresses {
        val res = api.addNewAddress(id,req)
        Log.i("AddressesRemoteSource", "res : $res")
        return gson.fromJson(
            res.body()!!.get("customer_address") as JsonObject,
            object : TypeToken<Addresses>() {}.type
        )
    }

    suspend fun updateAddress(customerID:String,addressID :String,req:RequestBody): Addresses {
        val res = api.updateAddress(customerID,addressID,req)
        Log.i("AddressesRemoteSource", "res : $res")
        return gson.fromJson(
            res.body()!!.get("customer_address") as JsonObject,
            object : TypeToken<Addresses>() {}.type
        )
    }

    suspend fun getAddressByCustomerID(customerID:String): ArrayList<Addresses> {
        val res = api.getAddressByCustomerID(customerID)
        Log.i("AddressesRemoteSource", "res : $res")
        return gson.fromJson(
            res.body()!!.get("addresses") as JsonArray,
            object : TypeToken<ArrayList<Addresses>>() {}.type
        )
    }

    suspend fun setDefaultAddress(customerID:String,addressID :String) {
        val res = api.setDefaultAddress(customerID,addressID)
        Log.i("AddressesRemoteSource", "res : $res")
    }

    suspend fun deleteAddressByID(customerID:String,addressID :String) {
        val res = api.deleteAddressByID(customerID,addressID)
        Log.i("AddressesRemoteSource", "res : $res")
    }
}