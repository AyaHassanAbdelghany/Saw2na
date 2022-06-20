package com.example.mcommerceapp.model.remote_source.coupon

import com.example.mcommerceadminapp.model.remote_source.coupon.ICoupon
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceadminapp.pojo.coupon.price_rule.PriceRules
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.copouns.ICouponsService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class CouponRemoteSource : ICoupon {

    private val api =
        ShopifyRetrofitHelper.getInstance().create(ICouponsService::class.java)
    private val gson = Gson()

    override suspend fun getAllPriceRules(): ArrayList<PriceRules> {
        val res = api.getAllPriceRules(Keys.PRICE_RULES_JSON)
        return gson.fromJson(
            res.body()!!.get(Keys.PRICE_RULES) as JsonArray,
            object : TypeToken<ArrayList<PriceRules>>() {}.type
        )
    }

    override suspend fun getAllDiscountCode(priceRuleID: String): ArrayList<DiscountCodes> {
        val res = api.getAllDiscountCode(Keys.PRICE_RULES, priceRuleID, Keys.DISCOUNT_CODE_JSON)
        return gson.fromJson(
            res.body()!!.get(Keys.DISCOUNT_CODE) as JsonArray,
            object : TypeToken<ArrayList<DiscountCodes>>() {}.type
        )
    }
}