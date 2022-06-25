package com.example.mcommerceapp.network.customer

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CustomerService {

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
    suspend fun updateCustomerByID(@Path("resource") id: String, @Body requestBody: RequestBody): Response<JsonObject>

}