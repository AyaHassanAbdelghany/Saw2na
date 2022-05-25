package com.example.mcommerceapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopifyServiceInterface {
    @GET("{resource}")
    suspend fun <T>getProducts(@Path("resource") resources: String): Response<T>
}