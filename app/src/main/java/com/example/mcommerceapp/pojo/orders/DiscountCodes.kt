package com.example.mcommerceapp.pojo.orders

import com.google.gson.annotations.SerializedName

data class DiscountCodes(
    @SerializedName("code") var code: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("type") var type: String? = null
)
