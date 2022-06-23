package com.example.mcommerceapp.model.draft_orders_repository

import android.util.Log
import com.example.mcommerceapp.model.draft_orders_repository.interfaces.ShoppingCartRepoInterface
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import draft_orders.DraftOrder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject


class DraftOrdersRepo private constructor(private val source: DraftOrdersRemoteSource) :
    ShoppingCartRepoInterface {
    companion object {

        private val draftOrdersRepo: DraftOrdersRepo? = null

        fun getInstance(remoteSource: DraftOrdersRemoteSource): DraftOrdersRepo {
            return draftOrdersRepo ?: DraftOrdersRepo(remoteSource)
        }
    }

    suspend fun createOrder(order: DraftOrder): DraftOrder {
        Log.d("object", order.toString())
        return source.createOrder(getRequest(order))
    }

    override suspend fun updateOrder(orderID: String, order: DraftOrder): DraftOrder {
        return source.updateOrder(orderID, getRequest(order))
    }

    override suspend fun getAllOrders(userEmail: String): ArrayList<DraftOrder> {
        return source.getAllOrders(userEmail)
    }

    suspend fun getOrderByID(orderID: String): DraftOrder {
        return source.getOrderByID(orderID)
    }

    override suspend fun deleteOrderByID(orderID: Long) {
        source.deleteOrderByID(orderID)
    }


    private fun getRequest(order: DraftOrder): RequestBody {

        val jsonArray = JSONArray()

        for (item in order.lineItems) {
            val json = JSONObject()
            json.put("product_id", item.productId)
            json.put("variant_id", item.variantId)
            json.put("quantity", item.quantity)
            json.put("title", item.title)
            json.put("price", item.price)

            jsonArray.put(json)
        }

        val jsonArrayImage = JSONArray()

        for (i in order.noteAttributes) {
            val j = JSONObject()
            Log.d("image", i.value!!)

            j.put("name", "image")
            j.put("value", i.value)

            jsonArrayImage.put(j)
        }


        val jsonReq = JSONObject()

        jsonReq.put("line_items", jsonArray)
        jsonReq.put("note_attributes", jsonArrayImage)
        jsonReq.put("note", order.note)


        jsonReq.put("email", order.email)

        val req = JSONObject()
        req.put("draft_order", jsonReq)

        val requestBody = req.toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.i("retrieveUsore", " $req ")

        return requestBody
    }

}