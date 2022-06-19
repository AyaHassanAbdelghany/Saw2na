package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceadminapp.model.shopify_repository.coupon.ICouponRepo
import com.example.mcommerceapp.model.draft_orders_repository.draft_orders.interfaces.ShoppingCartRepoInterface
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.GetUserCartRepo

class ShoppingCartViewModelFactory(
    private val iCartRepo: ShoppingCartRepoInterface,
    private val iGetUserCartRepo: GetUserCartRepo,
    private val iCouponsRepo: ICouponRepo
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            ShoppingCartViewModel(iCartRepo, iGetUserCartRepo, iCouponsRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }

}