package com.example.mcommerceapp.pojo.products

import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("id")
    var id: Double? = null,
    @SerializedName("product_id")
    var productId: Double? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("position")
    var position: Int? = null,
    @SerializedName("values")
    var values: ArrayList<String> = arrayListOf()

)
