package com.example.mcommerceapp.model.remote_source.orders

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.orders.OrdersService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import orders.Order


class OrdersRemoteSource {
    private val api: OrdersService =
        ShopifyRetrofitHelper.getInstance().create(OrdersService::class.java)
    private val gson = Gson()

    suspend fun createOrder(req: RequestBody):Order {
        val res = api.createOrder(req)
       return gson.fromJson(
            res.body()!!.get(Keys.ORDERS) as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun updateOrder(orderID: String,req: RequestBody):Order {
        val res = api.updateOrder(orderID,req)
        return gson.fromJson(
            res.body()!!.get(Keys.ORDERS) as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun getAllOrders(userID:String):ArrayList<Order> {
        val res = api.getAllOrders()
        val resOrders :ArrayList<Order> = gson.fromJson(
            res.body()!!.get(Keys.ORDERS) as JsonArray,
            object : TypeToken<ArrayList<Order>>() {}.type
        )

        val myOrders : ArrayList<Order> = arrayListOf()
        for (order in resOrders){
            if (order.customer?.id.toString() == userID) {
                myOrders.add(order)
            }
        }

        return myOrders
    }

    suspend fun getOrderByID(orderID:String):Order {
        val res = api.getOrderByID(orderID = orderID)
        return gson.fromJson(
            res.body()!!.get(Keys.ORDERS) as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun deleteOrderByID(orderID:String) {
        val res = api.deleteOrderByID(orderID = orderID)
    }

}