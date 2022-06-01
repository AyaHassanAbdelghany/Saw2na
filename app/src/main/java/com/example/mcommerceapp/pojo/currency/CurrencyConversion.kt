package com.example.mcommerceapp.pojo.currency

data class CurrencyConversion(
    val success: Boolean,
    val query: Query,
    val info: Info,
    val date: String,
    val result: Double
)