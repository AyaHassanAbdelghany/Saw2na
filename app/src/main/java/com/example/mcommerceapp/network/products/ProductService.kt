package com.example.mcommerceapp.network.products

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {


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
    ): Response<JsonObject>
}