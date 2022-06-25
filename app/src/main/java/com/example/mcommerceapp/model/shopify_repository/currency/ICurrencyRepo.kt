package com.example.mcommerceapp.model.shopify_repository.currency.interfaces

import com.example.mcommerceapp.pojo.currency.CurrencySymbols

interface StoredCurrency {

    fun getCurrencySymbol(): String
    fun getCurrencyValue(): Double

}

interface ICurrencyRepo {
    suspend fun getCurrencySymbols(): CurrencySymbols
    suspend fun convertCurrency(
        to: String
    )
}
