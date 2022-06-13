package com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductDetailRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM

class ProductDetailVMFactory(
    private val iProducts: ProductDetailRepo,
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