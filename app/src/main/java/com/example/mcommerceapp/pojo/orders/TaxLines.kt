package com.example.example

import com.google.gson.annotations.SerializedName
import orders.PriceSet


data class TaxLines (

  @SerializedName("price"          ) var price         : String?   = null,
  @SerializedName("rate"           ) var rate          : Double?   = null,
  @SerializedName("title"          ) var title         : String?   = null,
  @SerializedName("price_set"      ) var priceSet      : PriceSet? = PriceSet(),
  @SerializedName("channel_liable" ) var channelLiable : String?   = null

)