package com.example.mcommerceapp.pojo.smartcollections

import com.google.gson.annotations.SerializedName

data class SmartCollectionResponse (
    @SerializedName("smart_collections" )
    var smartCollections : ArrayList<SmartCollections> = arrayListOf()
)