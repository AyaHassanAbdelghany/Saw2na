package orders

import com.google.gson.annotations.SerializedName


data class TotalShippingPriceSet (

  @SerializedName("shop_money"        ) var shopMoney        : ShopMoney?        = ShopMoney(),
  @SerializedName("presentment_money" ) var presentmentMoney : PresentmentMoney? = PresentmentMoney()

)