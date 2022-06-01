package com.example.mcommerceapp.view.ui.more.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.currency_repository.interfaces.ICurrencyRepo
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.FirebaseAuthRepo
import com.example.mcommerceapp.pojo.currency.CurrencySymbols
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoreViewModel(private val userRepo: FirebaseAuthRepo , private val currencyRepo: ICurrencyRepo) : ViewModel() {

    private var _symbols = MutableLiveData<CurrencySymbols>()
    val symbols :LiveData<CurrencySymbols> = _symbols

    fun signOut(){
        userRepo.signOut()
    }
    fun convert(currency:String){
        viewModelScope.launch(Dispatchers.IO) {
           currencyRepo.convertCurrency(currency)


        }
    }

    fun getCurrencySymbols(){

        viewModelScope.launch(Dispatchers.IO) {
            val symbols = currencyRepo.getCurrencySymbols()

            withContext(Dispatchers.Main){
              _symbols.postValue(symbols)
            }
        }


    }


}