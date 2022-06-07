package com.example.mcommerceapp.model.remote_source.orders

import android.util.Log
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.orders.OrdersService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import orders.Order
import orders.Orders

class OrdersRemoteSource {
    private val api: OrdersService =
        ShopifyRetrofitHelper.getInstance().create(OrdersService::class.java)
    private val gson = Gson()

    suspend fun createOrder(req: RequestBody):Order {
        val res = api.createOrder(req)
        Log.i("OrdersRemoteSource", "\n\n\n\n\ncreateOrder: $res")

       return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun updateOrder(orderID: String,req: RequestBody):Order {
        val res = api.updateOrder(orderID,req)
        Log.i("OrdersRemoteSource", "\n\n\n\n\n updateOrder: ${res.body()}")

        return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun getAllOrders(userID:String):ArrayList<Order> {
        val res = api.getAllOrders()
        Log.i("OrdersRemoteSource", "\n\n\n\n\ngetAllOrders: ${res.body()}")

        val resOrders :Orders = gson.fromJson(
            res.body()!!.get("orders") as JsonArray,
            object : TypeToken<Orders>() {}.type
        )

        val myOrders : ArrayList<Order> = arrayListOf()
        for (order in resOrders.orders){
            if (order.customer?.id.toString() == userID)
                myOrders.add(order)
        }

        return myOrders
    }

    suspend fun getOrderByID(orderID:String):Order {
        val res = api.getOrderByID(orderID = orderID)
        Log.i("OrdersRemoteSource", "\n\n\n\n\ngetOrderByID: ${res.body()}")

        return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun deleteOrderByID(orderID:String) {
        val res = api.deleteOrderByID(orderID = orderID)
        Log.i("OrdersRemoteSource", "\n\n\n\ndeleteOrderByID: $res")
    }

}