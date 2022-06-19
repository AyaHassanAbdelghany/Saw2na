package com.example.mcommerceadminapp.model.remote_source.coupon

import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceadminapp.pojo.coupon.price_rule.PriceRules
import okhttp3.RequestBody

interface ICoupon {

    suspend fun getAllPriceRules(): ArrayList<PriceRules>
    suspend fun getAllDiscountCode(priceRuleID: String): ArrayList<DiscountCodes>
}
