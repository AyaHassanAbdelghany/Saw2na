package com.example.mcommerceapp.network

import com.example.mcommerceapp.pojo.products.ProductResponse
import com.example.mcommerceapp.pojo.products.ProductsFieldsResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ShopifyService {
    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun get(
        @Path("resource") resources: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getQuery(
        @Path("resource") resources: String, @Query("fields") fields: String
    ):  Response<JsonObject>

}