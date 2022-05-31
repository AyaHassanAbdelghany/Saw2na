package com.example.mcommerceapp.view.ui.favorite_product.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo

class FavoriteViewModelFactory(
    private val iFavRepo: IFavProductRoomRepo,
    private val myContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(iFavRepo, myContext) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}