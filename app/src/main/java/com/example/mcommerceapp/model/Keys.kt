package com.example.mcommerceapp.model

class Keys {
    companion object {
        val API_KEY = "9d169ad72dd7620e70f56b28ae6146d9"
        val PASSWORD = "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"
        val HOST_NAME = "madalex20220.myshopify.com"
        val VERSION = "2022-01"

        val BASE_URL: String =
            "https://" + API_KEY + ":" + PASSWORD + "@" + HOST_NAME + "/admin/api/" + VERSION + "/"

        val URL: String = ""
    }
}