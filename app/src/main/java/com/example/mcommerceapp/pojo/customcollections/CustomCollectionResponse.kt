package com.example.mcommerceapp.pojo.customcollections

import com.google.gson.annotations.SerializedName

data class CustomCollectionResponse(
    @SerializedName("custom_collections" )
    var customCollections : ArrayList<CustomCollections> = arrayListOf()
)
