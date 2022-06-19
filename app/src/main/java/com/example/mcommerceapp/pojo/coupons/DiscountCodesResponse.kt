package com.example.mcommerceadminapp.pojo.coupon.discount_code

import com.google.gson.annotations.SerializedName

data class DiscountCodesResponse (

    @SerializedName("discount_codes" )
    var discountCodes : ArrayList<DiscountCodes> = arrayListOf()

)