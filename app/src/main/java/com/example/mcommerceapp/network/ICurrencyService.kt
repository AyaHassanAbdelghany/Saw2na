package com.example.mcommerceapp.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ICurrencyService {
    @Headers("apikey: we2oc5jxArbNIo4NCN1Q4m3rGw6yRHcM")
    @GET("symbols")
    suspend fun getAllCurrencySymbols(): Response<JsonObject>


    @Headers("apikey: we2oc5jxArbNIo4NCN1Q4m3rGw6yRHcM")
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<JsonObject>
}