package orders

import com.google.gson.annotations.SerializedName


data class Orders (

  @SerializedName("order" ) var orders : ArrayList<Order> = arrayListOf()

)