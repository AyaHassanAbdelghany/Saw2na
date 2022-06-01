package com.example.mcommerceapp.model.currency_repository

import com.example.mcommerceapp.pojo.currency.CurrencyConversion
import com.example.mcommerceapp.pojo.currency.CurrencySymbols

interface ICurrencyRepo {
    suspend fun getCurrencySymbols(): CurrencySymbols
    suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): CurrencyConversion
}
