package com.example.mcommerceapp.network.copouns

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ICouponsService {
    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getAllPriceRules(
        @Path("resource", encoded = true) resources: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{priceRule}/{priceRuleId}/{resource}")
    suspend fun getAllDiscountCode(
        @Path("priceRule", encoded = true) orderID: String,
        @Path("priceRuleId", encoded = true) priceRuleId: String,
        @Path("resource", encoded = true) resource: String

    ): Response<JsonObject>
}