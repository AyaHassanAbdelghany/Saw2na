package com.example.example

import com.google.gson.annotations.SerializedName


data class CustomersResponse (

  @SerializedName("customers" ) var customers : ArrayList<Customers> = arrayListOf()

)