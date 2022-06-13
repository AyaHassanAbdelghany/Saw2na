package com.example.mcommerceapp.view.ui.product_detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductDetailRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import com.example.mcommerceapp.pojo.products.Products
import draft_orders.DraftOrder
import draft_orders.DraftOrders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Array

class ProductDetailVM(
    private val iProducts: ProductDetailRepo,
    private val iFavRepo: IFavProductRoomRepo,
    private val iCurrency: StoredCurrency,
    private val iOrder: DraftOrdersRepo,
    private val iUser: UserRepo
) : ViewModel() {

    private val _productDetail = MutableLiveData<Products>()
    var productDetail: LiveData<Products> = _productDetail

    private val _isFav = MutableLiveData<Int>()
    var isFav: LiveData<Int> = _isFav

    private val _order = MutableLiveData<DraftOrder>()
    var order : LiveData<DraftOrder> = _order

    val user = iUser.getUser()
    val isLogged = iUser.getLoggedInState()

    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    private var listFav = arrayListOf<Long>()
    private var _favList = MutableLiveData<ArrayList<Long>>()
    var favList : LiveData<ArrayList<Long>> = _favList

    var cartList = arrayListOf<String>()

    fun getProductDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = iProducts.getProductDetail(id)
            Log.d("Detail", detail.toString())
            withContext(Dispatchers.Main) {
                _productDetail.postValue(detail)
            }
        }
    }

    fun addOrder(order: DraftOrder){
        viewModelScope.launch {
            iOrder.createOrder(order)
        }
    }

    fun deleteOrder(id: String){
        viewModelScope.launch {
            iOrder.deleteOrderByID(id)
        }
    }

    fun getDraftOrder(){
        viewModelScope.launch {
            val order = iOrder.getAllOrders(user.userID)
            order.forEach {
                if(it.note == Keys.FAV){
                    listFav.add(it.lineItems[0].variantId!!)
                }
            }
            withContext(Dispatchers.Main){
                _favList.postValue(listFav)
            }
        }
    }


    fun checkForFavouriteProductById(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = iFavRepo.checkForFavoriteProductById(productId)
            withContext(Dispatchers.Main) {
                _isFav.postValue(value)
            }
        }
    }

    fun insertFavoriteProduct(product: FavProducts) {
        viewModelScope.launch(Dispatchers.IO) {
            iFavRepo.insertFavoriteProduct(product)
            checkForFavouriteProductById(product.productId)

        }
    }

    fun deleteFavoriteProduct(product: FavProducts) {
        viewModelScope.launch(Dispatchers.IO) {
            iFavRepo.deleteFavoriteProduct(product)
            checkForFavouriteProductById(product.productId)
        }
    }
}