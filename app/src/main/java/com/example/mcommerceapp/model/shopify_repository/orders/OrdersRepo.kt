package com.example.mcommerceapp.model.shopify_repository.orders

import android.util.Log
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import orders.Order
import org.json.JSONArray
import org.json.JSONObject

class OrdersRepo private constructor(private val source: OrdersRemoteSource) : IOrderPayment {

    companion object {
        private val ordersRepo: OrdersRepo? = null

        fun getInstance(remoteSource: OrdersRemoteSource): OrdersRepo {
            return ordersRepo ?: OrdersRepo(remoteSource)
        }
    }


    override suspend fun createOrder(order: Order): Order {
        return source.createOrder(getRequest(order))
    }

    suspend fun updateOrder(orderID: String, order: Order): Order {
        return source.updateOrder(orderID, getRequest(order))
    }

    suspend fun getAllOrders(userID: String): ArrayList<Order> {
        return source.getAllOrders(userID)
    }

    suspend fun getOrderByID(orderID: String): Order {
        return source.getOrderByID(orderID)
    }

    suspend fun deleteOrderByID(orderID: String) {
        source.deleteOrderByID(orderID)
    }


    private fun getRequest(order: Order): RequestBody {

        val jsonArray = JSONArray()

        for (item in order.lineItems) {
            val json = JSONObject()
            json.put("variant_id", item.variantId)
            json.put("quantity", item.quantity)

            jsonArray.put(json)
        }

        val shippingAddress = JSONObject()
        shippingAddress.put("address1", order.shippingAddress?.address1)
        shippingAddress.put("city", order.shippingAddress?.city)
        shippingAddress.put("country", order.shippingAddress?.country)
        shippingAddress.put("zip", order.shippingAddress?.zip)

        val discountCodes = JSONArray()
        for (code in order.discountCodes) {
            val discountCode = JSONObject()
            discountCode.put("code", code.code)
            discountCode.put("amount", code.amount)
            discountCode.put("type", code.type)

            discountCodes.put(discountCode)
        }

        val jsonReq = JSONObject()
        jsonReq.put("line_items", jsonArray)
        jsonReq.put("email", order.email)
        jsonReq.put("shipping_address", shippingAddress)
        jsonReq.put("discount_codes", discountCodes)

        val req = JSONObject()
        req.put("order", jsonReq)

        val requestBody = req.toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.i("order", "retrieveUserFromFireStore:  $req ")

        return requestBody
    }

}