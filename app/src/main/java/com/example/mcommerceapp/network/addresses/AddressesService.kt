package com.example.mcommerceapp.network.addresses

import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AddressesService {

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @GET("customers/{resource}/addresses.json")
    suspend fun getAddressByCustomerID(
        @Path("resource", encoded = true) customerID: String
    ): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @POST("customers/{resource}/addresses.json")
    suspend fun addNewAddress(
        @Path("resource", encoded = true) customerID: String,
        @Body requestBody: RequestBody
    ): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @PUT("customers/{customerID}/addresses/{addressID}.json")
    suspend fun updateAddress(
        @Path("customerID", encoded = true) customerID: String,
        @Path("addressID", encoded = true) addressID: String,
        @Body requestBody: RequestBody
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @PUT("customers/{customerID}/addresses/{addressID}/default.json")
    suspend fun setDefaultAddress(
        @Path("customerID", encoded = true) customerID: String,
        @Path("addressID", encoded = true) addressID: String
    ): Response<JsonObject>



    @Headers(
        "X-Shopify-Access-Token: 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",
        "Content-Type: application/json"
    )
    @DELETE("customers/{customerID}/addresses/{addressID}.json")
    suspend fun deleteAddressByID(
        @Path("customerID", encoded = true) customerID: String,
        @Path("addressID", encoded = true) addressID: String,
    ): Response<JsonObject>


}