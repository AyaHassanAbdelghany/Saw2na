package com.example.mcommerceapp.model

import com.example.mcommerceapp.pojo.products.Review

class Keys {
    companion object {

        const val PRODUCTS ="products.json"
        const val SMART_COLLECTIONS ="smart_collections.json"
        const val CUSTOM_COLLECTIONS ="custom_collections.json"


        val COLLECTION ="collection"
        val ALL_PRODUCT ="allProduct"
        val VENDOR ="vendor"

        const val CART = "cart"
        const val FAV = "fav"

        val PRODUCT ="product"
        val CATEGORY ="category"


        const val ORDERS ="orders"
        const val JSON_Extension = ".json"


        const val PRODUCT_TYPE ="product_type"
        const val BASE_URL: String ="https://madalex20220.myshopify.com/admin/api/2022-01/"

        val REVIEWS = listOf(
            Review(name = "Mariam", rate = 5.0F, date = "05/04/2021", desc = "This is so cool and very comfortable."),
            Review(name = "Mahmoud", rate = 3.5F, date = "25/10/2021", desc = "This is so cool and very comfortable."),
            Review(name = "Alaa", rate = 4.0F, date = "01/01/2021", desc = "This is so cool and very comfortable."),
            Review(name = "Ali", rate = 4.5F, date = "16/06/2021", desc = "This is so cool and very comfortable."),

        )

        const val CURRENCY_BASE_URL = "https://api.apilayer.com/exchangerates_data/"
    }
}