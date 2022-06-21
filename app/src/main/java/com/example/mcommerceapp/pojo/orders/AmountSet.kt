package com.example.mcommerceapp.pojo.orders

import com.google.gson.annotations.SerializedName
import orders.PresentmentMoney
import orders.ShopMoney

data class AmountSet(
    @SerializedName("shop_money") var shopMoney: ShopMoney? = ShopMoney(),
    @SerializedName("presentment_money") var presentmentMoney: PresentmentMoney? = PresentmentMoney()
)
