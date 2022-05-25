package com.example.mcommerceapp.network

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.pojo.MyObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShopifyRetrofitClient {
    companion object {
        private var retrofit: Retrofit? = null

        fun getInstance(): Retrofit {
            if (retrofit == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                var client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(Keys.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit!!
        }
/*
        suspend fun <T>getData():T
        {
            val s: ShopifyServiceInterface =
                getInstance().create(ShopifyServiceInterface::class.java)
            var res = s.getProducts<T>("H")

        }

        var x = getData<MyObject>()*/
    }
}