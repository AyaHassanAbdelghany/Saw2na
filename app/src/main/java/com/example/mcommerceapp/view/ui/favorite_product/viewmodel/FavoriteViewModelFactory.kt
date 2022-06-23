package com.example.mcommerceapp.view.ui.favorite_product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.user_repository.UserRepo

class FavoriteViewModelFactory(
    private val iFavRepo: IFavProductRoomRepo,
    private val iOrder: DraftOrdersRepo,
    private val iUser: UserRepo,
    private val iCurrencyRepo: StoredCurrency
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(iFavRepo, iOrder, iUser, iCurrencyRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}