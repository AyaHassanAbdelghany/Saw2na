package com.example.mcommerceadminapp.model.shopify_repository.coupon


import androidx.lifecycle.MutableLiveData
import com.example.mcommerceadminapp.model.remote_source.coupon.ICoupon
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceadminapp.pojo.coupon.price_rule.PriceRules
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CouponRepo private  constructor(private var iCouponRemoteSource : ICoupon):ICouponRepo {

    companion object {
        private val couponRepo: CouponRepo? = null

        fun getInstance(iCoupon: ICoupon): CouponRepo {
            return couponRepo ?: CouponRepo(iCoupon)
        }
    }

    override suspend fun getAllPriceRules() :ArrayList<PriceRules>{
        return iCouponRemoteSource.getAllPriceRules()
    }

    override suspend fun getAllDiscountCode(priceRuleID:String) : ArrayList<DiscountCodes>{
       return iCouponRemoteSource.getAllDiscountCode(priceRuleID)
    }

}