package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceadminapp.model.shopify_repository.coupon.ICouponRepo
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.draft_orders.interfaces.ShoppingCartRepoInterface
import com.example.mcommerceapp.model.shopify_repository.user.user_repo_interfaces.GetUserCartRepo

class ShoppingCartViewmodelFactory(
    private val iCartRpo: ShoppingCartRepoInterface,
    private val iGetUserCartRepo: GetUserCartRepo,
    private val iCouponsRepo: ICouponRepo,
    private val iCurrencyRepo: StoredCurrency
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewmodel::class.java)) {
            ShoppingCartViewmodel(iCartRpo, iGetUserCartRepo, iCouponsRepo, iCurrencyRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}