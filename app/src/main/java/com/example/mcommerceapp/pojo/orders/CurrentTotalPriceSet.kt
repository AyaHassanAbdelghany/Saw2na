package orders

import com.google.gson.annotations.SerializedName


data class CurrentTotalPriceSet (

  @SerializedName("shop_money"        ) var shopMoney        : ShopMoney?        = ShopMoney(),
  @SerializedName("presentment_money" ) var presentmentMoney : PresentmentMoney? = PresentmentMoney()

)