package com.example.mcommerceapp.model.shopify_repository.currency

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.ICurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.pojo.currency.CurrencySymbols

class CurrencyRepo private constructor(private var remoteSource: ProductRemoteSource, private val context: Context) : StoredCurrency ,ICurrencyRepo {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("currency", 0)
    companion object {
        private val currencyRepo: CurrencyRepo? = null

        fun getInstance(remoteSource: ProductRemoteSource, context: Context): CurrencyRepo {
            return currencyRepo ?: CurrencyRepo(remoteSource,context)
        }
    }

    override suspend fun getCurrencySymbols(): CurrencySymbols {
        return remoteSource.getCurrencySymbols()
    }

    override suspend fun convertCurrency(to: String) {
        val res = remoteSource.convertCurrency("EGP", to, 1.0)
        sharedPreferences.edit().putString("value","${res.result}").putString("symbol", to).apply()
    }

    override fun getCurrencySymbol(): String {
        return sharedPreferences.getString("symbol","EGP")!!
    }

    override fun getCurrencyValue(): Double {
        return sharedPreferences.getString("value","1.0")!!.toDouble()
    }
}