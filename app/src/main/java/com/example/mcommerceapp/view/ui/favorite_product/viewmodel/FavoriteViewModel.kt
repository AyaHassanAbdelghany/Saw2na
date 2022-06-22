package com.example.mcommerceapp.view.ui.favorite_product.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.currency_repository.interfaces.ICurrencyRepo
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val iFavRepo: IFavProductRoomRepo,
    private val iOrder: DraftOrdersRepo,
    private val iUser: UserRepo,
    private val iCurrencyRepo: StoredCurrency
) : ViewModel() {

    private var favProductsMutableLiveData = MutableLiveData<List<FavProducts>>()
    var favProductsLiveData: LiveData<List<FavProducts>> = favProductsMutableLiveData

    private var inFavMutableLiveData = MutableLiveData<Int>()
    var inFavLiveData: LiveData<Int> = inFavMutableLiveData

    val user = iUser.getUser()

    val symbol = iCurrencyRepo.getCurrencySymbol()
    val value = iCurrencyRepo.getCurrencyValue()

    private var listFav = arrayListOf<DraftOrder>()
    private var _favList = MutableLiveData<ArrayList<DraftOrder>>()
    var favList: LiveData<ArrayList<DraftOrder>> = _favList

    private fun getAllFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = iFavRepo.getAllFavoriteProducts()
            withContext(Dispatchers.Main) {
                favProductsMutableLiveData.postValue(products)
            }
        }
    }

    fun getDraftOrder() {
        viewModelScope.launch {
            val order = iOrder.getAllOrders(user.userID)
            withContext(Dispatchers.Main) {
                order.forEach {
                    if (it.note == Keys.FAV) {
                        if (it.lineItems[0].productId != null)
                            listFav.add(it)
                    }
                }
                _favList.postValue(listFav)
            }
        }
    }

    fun deleteOrder(id: Long) {
        viewModelScope.launch {
            val order = iOrder.getAllOrders(user.userID)
            Log.d("idddddddddd", id.toString())
            order.forEach {
                Log.d("idddddddddd", it.lineItems[0].productId.toString())
                if (it.lineItems[0].productId == id) {
                    Log.d("TEst", it.orderId.toString())
                    iOrder.deleteOrderByID(it.id!!)
                }
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