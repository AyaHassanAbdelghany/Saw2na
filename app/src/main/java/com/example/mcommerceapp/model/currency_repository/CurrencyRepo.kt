package com.example.mcommerceapp.model.currency_repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mcommerceapp.model.currency_repository.interfaces.ICurrencyRepo
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.currency.CurrencySymbols

class CurrencyRepo private constructor(private var remoteSource: RemoteSource,private val context: Context) : StoredCurrency ,ICurrencyRepo {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("currency", 0)
    companion object {
        private val currencyRepo: CurrencyRepo? = null

        fun getInstance(remoteSource: RemoteSource,context: Context): CurrencyRepo {
            return currencyRepo ?: CurrencyRepo(remoteSource,context)
        }
    }

    override suspend fun getCurrencySymbols(): CurrencySymbols {
        return remoteSource.getCurrencySymbols()
    }

    override suspend fun convertCurrency(to: String) {
        val res = remoteSource.convertCurrency("EGP", to, 1.0)
        sharedPreferences.edit().putString("value","${res.result}").putString("symbol", to).apply()
        Log.i("convertCurrency", "convertCurrency: ${sharedPreferences.getString("value","1.0")}  ${sharedPreferences.getString("symbol","USD")}")

    }

    override fun getCurrencySymbol(): String {
        return sharedPreferences.getString("symbol","EGP")!!
    }

    override fun getCurrencyValue(): Double {
        return sharedPreferences.getString("value","1.0")!!.toDouble()
    }
}