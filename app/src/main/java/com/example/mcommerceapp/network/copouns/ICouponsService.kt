package com.example.mcommerceapp.network.copouns

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ICouponsService {
    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("orders.json")
    suspend fun getAllOrders(): Response<JsonObject>


    @Headers("apikey:ZckheMOjLcIIs5QIjoWrVXQL6geNwOPA")
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<JsonObject>
}