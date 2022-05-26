package com.example.mcommerceapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ShopifyService {
    @Headers("X-Shopify-Access-Token: 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"
        , "Content-Type: application/json")
    @GET("{resource}")
    suspend fun <T>getProducts(@Path("resource") resources: String): Response<T>
}