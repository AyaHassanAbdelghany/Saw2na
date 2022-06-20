package com.example.mcommerceadminapp.pojo.coupon.discount_code

import com.google.gson.annotations.SerializedName

data class DiscountCodes(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("price_rule_id")
    var priceRuleId: Int? = null ,
    @SerializedName("code")
    var code: String? = null,
    @SerializedName("usage_count")
    var usageCount: Int?  = null,
    @SerializedName("created_at")
    var createdAt: String?  = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null


)
