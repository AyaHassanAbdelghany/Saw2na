package com.example.mcommerceapp.model.remote_source.orders

import android.util.Log
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.orders.OrdersService
import com.example.mcommerceapp.pojo.products.Variants
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

    suspend fun createOrder(req: RequestBody): Order {
        val res = api.createOrder(req)
        Log.i("OrdersRemoteSource", "\n\n\n\n\ncreateOrder: $res")

        return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun updateOrder(orderID: String, req: RequestBody): Order {
        val res = api.updateOrder(orderID, req)
        Log.i("OrdersRemoteSource", "\n\n\n\n\n updateOrder: $res")

        return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun getAllOrders(userID: String): ArrayList<Order> {
        val res = api.getAllOrders()
        Log.i("OrdersRemoteSource", "\n\n\n\n\ngetAllOrders: ${res.body()}")

        val resOrders: ArrayList<Order> = gson.fromJson(
            res.body()!!.get("orders") as JsonArray,
            object : TypeToken<ArrayList<Order>>() {}.type
        )

        val myOrders: ArrayList<Order> = arrayListOf()
        for (order in resOrders) {
            if (order.customer?.id.toString() == userID) {
                myOrders.add(order)
            }
        }

        return myOrders
    }

    suspend fun getOrderByID(orderID: String): Order {
        val res = api.getOrderByID(orderID = orderID)
        Log.i("OrdersRemoteSource", "\n\n\n\n\ngetOrderByID: $res")
        return gson.fromJson(
            res.body()!!.get("order") as JsonObject,
            object : TypeToken<Order>() {}.type
        )
    }

    suspend fun deleteOrderByID(orderID: String) {
        val res = api.deleteOrderByID(orderID = orderID)
        Log.i("OrdersRemoteSource", "\n\n\n\ndeleteOrderByID: $res")
    }

    suspend fun adjustInventoryItem(req: RequestBody) {
        val res = api.adjustInventoryLevel(requestBody = req)
        Log.i("OrdersRemoteSource", "\n\n\n\nadjustInventoryItem: $res")
    }

    suspend fun getVariantByID(variantID:String):Variants{
        val res = api.getVariantByID(variantID = variantID)
        Log.i("OrdersRemoteSource", "\n\n\n\n\ngetVariantByID: $res")
        return gson.fromJson(
            res.body()!!.get("variant") as JsonObject,
            object : TypeToken<Variants>() {}.type
        )
    }
}