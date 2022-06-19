package com.example.mcommerceapp.pojo.orders

import com.google.gson.annotations.SerializedName

data class NoteAttributes (

    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("value"       ) var value : String? = null
)