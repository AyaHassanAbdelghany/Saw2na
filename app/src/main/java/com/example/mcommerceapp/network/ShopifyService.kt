package com.example.mcommerceapp.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopifyService {
    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun get(
        @Path("resource", encoded = true) resources: String
    ): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCategoryForCollection(
        @Path("resource") resources: String,
        @Query("fields") fields: String,
        @Query("collection_id") collection_id: String
    ):  Response<JsonObject>

    suspend fun getQuery(
        @Path("resource") resources: String, @Query("fields") fields: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")

    suspend fun getSubCollection(
        @Path("resource") resources: String,
        @Query("fields") fields: String,
    ):  Response<JsonObject>

    suspend fun getQuery(
        @Path("resource") resources: String, @Query("fields") fields: String
    ): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCategoryForVendor(
        @Path("resource") resources: String,
        @Query("fields") fields: String
        ,@Query("collection_id") collection_id :String
       ,@Query("vendor") vendor :String
    ):  Response<JsonObject>
}