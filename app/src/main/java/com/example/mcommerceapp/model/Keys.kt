package com.example.mcommerceapp.model

import com.example.mcommerceapp.pojo.products.Review

class Keys {
    companion object {
        const val PRODUCTS_URL = "products.json"
        const val SMART_COLLECTIONS_URL = "smart_collections.json"
        const val CUSTOM_COLLECTIONS_URL = "custom_collections.json"
        const val PRICE_RULES = "price_rules"
        const val DISCOUNT_CODE = "discount_codes"
        const val PRICE_RULES_JSON = "price_rules.json"
        const val DISCOUNT_CODE_JSON = "discount_codes.json"

        const val ACCESS_TOKEN = "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"


        const val COLLECTION = "collection"
        const val ALL_PRODUCT = "allProduct"
        const val VENDOR = "vendor"

        const val CART = "cart"
        const val FAV = "fav"

        const val ORDERS = "orders"
        const val DraftORDERS = "draft_orders"
        const val DraftORDER = "draft_order"
        const val PRODUCTS = "products"
        const val PRODUCT = "product"
        const val CUSTOMER = "customer"
        const val SMART_COLLECTIONS = "smart_collections"
        const val CUSTOM_COLLECTIONS = "custom_collections"
        const val CUSTOMER_ADDRESS = "customer_address"
        const val ADDRESS = "addresses"
        const val JSON_Extension = ".json"
        const val INVENTORY_LOCATION = 65322057866


        const val PRODUCT_TYPE = "product_type"
        const val BASE_URL: String = "https://madalex2022-android.myshopify.com/admin/api/2022-01/"

        val REVIEWS = listOf(
            Review(
                name = "Mariam",
                rate = 5.0F,
                date = "05/04/2021",
                desc = "This is so cool and very comfortable."
            ),
            Review(
                name = "Mahmoud",
                rate = 3.5F,
                date = "25/10/2021",
                desc = "This is so cool and very comfortable."
            ),
            Review(
                name = "Alaa",
                rate = 4.0F,
                date = "01/01/2021",
                desc = "This is so cool and very comfortable."
            ),
            Review(
                name = "Ali",
                rate = 4.5F,
                date = "16/06/2021",
                desc = "This is so cool and very comfortable."
            ),

            )

        const val CURRENCY_BASE_URL = "https://api.apilayer.com/exchangerates_data/"
    }
}