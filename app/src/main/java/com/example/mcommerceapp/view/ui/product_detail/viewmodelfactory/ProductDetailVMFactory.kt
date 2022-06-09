package com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductDetailRepo
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM

class ProductDetailVMFactory(
    private val iProducts: ProductDetailRepo,
    private val iFavRepo: IFavProductRoomRepo,
    private val iCurrency: StoredCurrency
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailVM::class.java)) {
            ProductDetailVM(iProducts, iFavRepo, iCurrency) as T
        } else {
            throw IllegalArgumentException("Error")
        }
    }
}