package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.draft_orders.interfaces.ShoppingCartRepoInterface

class ShoppingCartViewmodelFactory(
    private val iCartRpo: ShoppingCartRepoInterface,
    private val myContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewmodel::class.java)) {
            ShoppingCartViewmodel(iCartRpo, myContext) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }

}