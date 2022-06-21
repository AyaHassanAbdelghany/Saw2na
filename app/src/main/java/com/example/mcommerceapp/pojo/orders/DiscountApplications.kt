package com.example.mcommerceapp.pojo.orders

import com.google.gson.annotations.SerializedName

data class DiscountApplications(
    @SerializedName("target_type") var targetType: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("value_type") var valueType: String? = null,
    @SerializedName("allocation_method") var allocationMethod: String? = null,
    @SerializedName("target_selection") var targetSelection: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null
)
