package com.example.mcommerceapp.pojo.products

import com.google.gson.annotations.SerializedName

data class ProductsFieldsResponse(
    @SerializedName("products" )
    var products : HashSet<ProductFields>
    )