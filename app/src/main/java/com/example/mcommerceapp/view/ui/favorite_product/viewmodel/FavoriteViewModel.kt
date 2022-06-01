package com.example.mcommerceapp.view.ui.favorite_product.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val iFavRepo: IFavProductRoomRepo,
    private val myContext: Context
) : ViewModel() {

    private var favProductsMutableLiveData = MutableLiveData<List<FavProducts>>()
    var favProductsLiveData: LiveData<List<FavProducts>> = favProductsMutableLiveData

    private var inFavMutableLiveData = MutableLiveData<Int>()
    var inFavLiveData: LiveData<Int> = inFavMutableLiveData

    fun getAllFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = iFavRepo.getAllFavoriteProducts()
            withContext(Dispatchers.Main) {
                favProductsMutableLiveData.postValue(products)
            }
        }
    }

    fun checkForFavoriteProductById(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = iFavRepo.checkForFavoriteProductById(productId)
            withContext(Dispatchers.Main) {
                inFavMutableLiveData.postValue(value)
            }
        }
    }

    fun insertFavoriteProduct(product: FavProducts) {
        viewModelScope.launch(Dispatchers.IO) {
            iFavRepo.insertFavoriteProduct(product)
            withContext(Dispatchers.Main) {
                getAllFavoriteProducts()
            }
        }
    }

    fun deleteFavoriteProduct(product: FavProducts) {
        viewModelScope.launch(Dispatchers.IO) {
            iFavRepo.deleteFavoriteProduct(product)
            withContext(Dispatchers.Main) {
                getAllFavoriteProducts()
            }
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            iFavRepo.deleteAllProducts()
            withContext(Dispatchers.Main) {
                getAllFavoriteProducts()
            }
        }
    }
}