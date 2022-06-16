package com.example.mcommerceapp.model.remote_source.orders

import android.util.Log
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
        Log.i("DraftOrdersRemoteSource", "\n\n\n\n\ncreateOrder: ${res}")

        return gson.fromJson(
            res.body()!!.get("draft_order") as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun updateOrder(orderID: String, req: RequestBody): DraftOrder {
        val res = api.updateDraftOrder(orderID, req)
        Log.i("DraftOrdersRemoteSource", "\n\n\n\n\n updateOrder: $res")

        return gson.fromJson(
            res.body()!!.get("draft_order") as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun getAllOrders(userID: String): ArrayList<DraftOrder> {
        val res = api.getAllDraftOrders()
        Log.i("DraftOrdersRemoteSource", "\n\n\n\n\ngetAllOrders: $res")

        val resOrders: ArrayList<DraftOrder> = gson.fromJson(
            res.body()!!.get("draft_orders") as JsonArray,
            object : TypeToken<ArrayList<DraftOrder>>() {}.type
        )

        val myOrders: ArrayList<DraftOrder> = arrayListOf()

        for (order in resOrders) {
//            if (order.id != 872900919435) {
//                deleteOrderByID(order.id!!)
            if (order.customer?.id.toString() == userID) {
                myOrders.add(order)
            }
//            }
        }

        return myOrders
    }

    suspend fun getOrderByID(orderID: String): DraftOrder {
        val res = api.getDraftOrderByID(orderID = orderID)
        Log.i("DraftOrdersRemoteSource", "\n\n\n\n\ngetOrderByID: $res")

        return gson.fromJson(
            res.body()!!.get("draft_order") as JsonObject,
            object : TypeToken<DraftOrder>() {}.type
        )
    }

    suspend fun deleteOrderByID(orderID: Long) {
        val res = api.deleteDraftOrderByID(orderID = orderID)
        Log.i("DraftOrdersRemoteSource", "\n\n\n\ndeleteOrderByID: $res")
    }

}