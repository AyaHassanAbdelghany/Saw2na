package com.example.mcommerceapp.network.product

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ShopifyService {

    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getAllProducts(
        @Path("resource", encoded = true) resources: String
    ): Response<JsonObject>

    //////////////////
    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCollectionProducts(
        @Path("resource", encoded = true) resources: String,
        @Query("collection_id") collection_id: String
    ): Response<JsonObject>
//////////////////

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getProductsVendor(
        @Path("resource", encoded = true) resources: String,
        @Query("vendor") vendor: String,
    ): Response<JsonObject>
    //////////////////

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getProductsCollection(
        @Path("resource") resources: String,
        @Query("collection_id") collection_id: String
    ): Response<JsonObject>

    ////////////////
    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getCollectionId(
        @Path("resource") resources: String, @Query("title") fields: String
    ): Response<JsonObject>

    //////////
    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("{resource}")
    suspend fun getSubCollection(
        @Path("resource") resources: String,
        @Query("fields") fields: String,
    ):  Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @POST("/admin/api/2021-10/customers.json")
    suspend fun createCustomer(@Body requestBody: RequestBody): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("/admin/api/2021-10/customers/{resource}.json")
    suspend fun getCustomerByID( @Path("resource") id: String): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @PUT("/admin/api/2021-10/customers/{resource}.json")
    suspend fun updateCustomerByID( @Path("resource") id: String,@Body requestBody: RequestBody): Response<JsonObject>

}