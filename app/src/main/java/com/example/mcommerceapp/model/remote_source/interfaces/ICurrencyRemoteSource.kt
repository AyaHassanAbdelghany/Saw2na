package com.example.mcommerceapp.model.remote_source.interfaces

import com.example.mcommerceapp.pojo.currency.CurrencyConversion
import com.example.mcommerceapp.pojo.currency.CurrencySymbols

interface ICurrencyRemoteSource {
    suspend fun getCurrencySymbols(): CurrencySymbols
    suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): CurrencyConversion
}