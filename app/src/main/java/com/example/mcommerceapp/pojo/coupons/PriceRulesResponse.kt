package com.example.mcommerceapp.pojo.coupon.price_rule

import com.example.mcommerceadminapp.pojo.coupon.price_rule.PriceRules
import com.google.gson.annotations.SerializedName

data class PriceRuleRespons (

    @SerializedName("price_rules" )
    var priceRules : ArrayList<PriceRules>

    )