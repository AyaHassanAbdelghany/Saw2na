package com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.draft_orders.DraftOrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.IProductDetailRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM

class ProductDetailVMFactory(
    private val iProducts: IProductDetailRepo,
    private val iFavRepo: IFavProductRoomRepo,
    private val iCurrency: StoredCurrency,
    private val iOrder: DraftOrdersRepo,
    private val iUser: UserRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailVM::class.java)) {
            ProductDetailVM(iProducts, iFavRepo, iCurrency, iOrder, iUser) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}