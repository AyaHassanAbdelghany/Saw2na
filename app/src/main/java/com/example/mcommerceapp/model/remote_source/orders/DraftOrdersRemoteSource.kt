package com.example.mcommerceapp.model.remote_source.orders

import android.util.Log
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.orders.DraftOrdersService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import draft_orders.DraftOrder
import okhttp3.RequestBody

class DraftOrdersRemoteSource private constructor() {
    companion object {
        private var draftOrdersRemoteSource: DraftOrdersRemoteSource? = null

        fun getInstance(): DraftOrdersRemoteSource {
            return draftOrdersRemoteSource ?: DraftOrdersRemoteSource()
        }
    }

    private val api: DraftOrdersService =
        ShopifyRetrofitHelper.getInstance().create(DraftOrdersService::class.java)
    private val gson = Gson()

    suspend fun createOrder(req: RequestBody): DraftOrder {
        val res = api.createDraftOrder(req)
        return gson.fromJson(
            res.body()!!.get(Keys.DraftORDER) as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun updateOrder(orderID: String, req: RequestBody): DraftOrder {
        val res = api.updateDraftOrder(orderID, req)
        return gson.fromJson(
            res.body()!!.get(Keys.DraftORDERS) as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun getAllOrders(userID: String): ArrayList<DraftOrder> {
        val res = api.getAllDraftOrders()
        val resOrders: ArrayList<DraftOrder> = gson.fromJson(
            res.body()!!.get(Keys.DraftORDERS) as JsonArray,
            object : TypeToken<ArrayList<DraftOrder>>() {}.type
        )

        val myOrders: ArrayList<DraftOrder> = arrayListOf()
        for (order in resOrders) {
            if (order.customer?.id.toString() == userID) {
                myOrders.add(order)
            }
        }

        return myOrders
    }

    suspend fun getOrderByID(orderID: String): DraftOrder {
        val res = api.getDraftOrderByID(orderID = orderID)
        return gson.fromJson(
            res.body()!!.get(Keys.DraftORDERS) as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun deleteOrderByID(orderID: Long) {
        val res = api.deleteDraftOrderByID(orderID = orderID)
    }

}