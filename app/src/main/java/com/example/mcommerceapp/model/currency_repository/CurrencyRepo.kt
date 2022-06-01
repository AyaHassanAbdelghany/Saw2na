package com.example.mcommerceapp.model.currency_repository

import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.currency.CurrencyConversion
import com.example.mcommerceapp.pojo.currency.CurrencySymbols

class CurrencyRepo private constructor(private var remoteSource: RemoteSource) : ICurrencyRepo {

    companion object {
        private val currencyRepo: CurrencyRepo? = null

        fun getInstance(remoteSource: RemoteSource): CurrencyRepo {
            return currencyRepo ?: CurrencyRepo(remoteSource)
        }
    }

    override suspend fun getCurrencySymbols(): CurrencySymbols {
        return remoteSource.getCurrencySymbols()
    }

    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): CurrencyConversion {
        return remoteSource.convertCurrency(from, to, amount)
    }
}