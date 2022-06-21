package com.example.mcommerceapp.pojo.orders

import com.google.gson.annotations.SerializedName

data class DiscountAllocations(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("amount_set") var amountSet: AmountSet? = AmountSet(),
    @SerializedName("discount_application_index") var discountApplicationIndex: Int? = null
)
