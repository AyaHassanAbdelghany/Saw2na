package com.example.mcommerceapp.network

import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

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
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")

    suspend fun getSubCollection(
        @Path("resource") resources: String,
        @Query("fields") fields: String,
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getProductCollection(
        @Path("resource") resources: String,
        @Query("product_type") product_type: String,
        @Query("collection_id") collection_id: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getProductVendor(
        @Path("resource") resources: String,
        @Query("product_type") product_type: String,
        @Query("vendor") vendor: String,
        @Query("collection_id") collection_id: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getQuery(
        @Path("resource") resources: String, @Query("fields") fields: String
    ): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCollectionId(
        @Path("resource") resources: String, @Query("title") fields: String
    ): Response<JsonObject>
    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCategoryForVendor(
        @Path("resource") resources: String,
        @Query("fields") fields: String,
        @Query("collection_id") collection_id: String,
        @Query("vendor") vendor: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )

    @POST("/admin/api/2021-10/customers.json")
    suspend fun createCustomer(@Body requestBody: RequestBody): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("/admin/api/2021-10/customers/{resource}.json")
    suspend fun getCustomerByID( @Path("resource") id: String): Response<JsonObject>


}