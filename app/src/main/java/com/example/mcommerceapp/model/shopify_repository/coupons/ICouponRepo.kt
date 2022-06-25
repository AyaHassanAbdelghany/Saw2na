package com.example.mcommerceadminapp.model.shopify_repository.coupon

import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceadminapp.pojo.coupon.price_rule.PriceRules

interface ICouponRepo {

    suspend fun getAllPriceRules(): ArrayList<PriceRules>
    suspend fun getAllDiscountCode(priceRuleID:String): ArrayList<DiscountCodes>
}