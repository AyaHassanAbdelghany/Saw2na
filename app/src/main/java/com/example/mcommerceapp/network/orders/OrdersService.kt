package com.example.mcommerceapp.network.orders

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface OrdersService {

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("orders/{resource}.json")
    suspend fun getOrderByID(
        @Path("resource", encoded = true) orderID: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}5",
        "Content-Type: application/json"
    )
    @GET("orders.json")
    suspend fun getAllOrders(): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @POST("orders.json")
    suspend fun createOrder(@Body requestBody: RequestBody): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @PUT("orders/{resource}.json")
    suspend fun updateOrder( @Path("resource", encoded = true) orderID: String,@Body requestBody: RequestBody): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @DELETE("orders/{resource}.json")
    suspend fun deleteOrderByID(
        @Path("resource", encoded = true) orderID: String
    ): Response<JsonObject>


}