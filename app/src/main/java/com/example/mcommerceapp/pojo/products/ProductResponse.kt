package com.example.mcommerceapp.pojo.products

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products" ) var products : ArrayList<Products>?
)
