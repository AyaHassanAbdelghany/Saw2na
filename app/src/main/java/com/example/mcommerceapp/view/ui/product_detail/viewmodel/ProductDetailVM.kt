package com.example.mcommerceapp.view.ui.product_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.draft_orders.DraftOrdersRepo
import com.example.mcommerceapp.model.room_repository.IFavProductRoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.IProductDetailRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import com.example.mcommerceapp.pojo.products.Products
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailVM(
    private val iProducts: IProductDetailRepo,
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
    var order: LiveData<DraftOrder> = _order

    val user = iUser.getUser()

    val currencySymbol = iCurrency.getCurrencySymbol()
    val currencyValue = iCurrency.getCurrencyValue()

    private var listFav = arrayListOf<Long>()
    private var _favList = MutableLiveData<ArrayList<Long>>()
    var favList: LiveData<ArrayList<Long>> = _favList

    private var listCart = hashSetOf<Long>()
    private var _cartList = MutableLiveData<HashSet<Long>>()
    var cartList: LiveData<HashSet<Long>> = _cartList

    fun getProductDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = iProducts.getProductDetail(id)
            withContext(Dispatchers.Main) {
                _productDetail.postValue(detail)
                getDraftOrder()
            }
        }
    }

    fun addOrder(order: DraftOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val newOrder = iOrder.createOrder(order)
            withContext(Dispatchers.Main) {
                if (newOrder.note == Keys.CART) {
                    listCart.add(newOrder.lineItems[0].variantId!!)
                    _cartList.postValue(listCart)
                } else if (newOrder.note == Keys.FAV) {
                    listFav.add(newOrder.lineItems[0].productId!!)
                    _favList.postValue(listFav)
                }
            }
        }
    }

    fun deleteOrder(id: Long) {
        viewModelScope.launch {
            val order = iOrder.getAllOrders(user.email)
            withContext(Dispatchers.Main) {
                order.forEach {
                    if (it.lineItems[0].productId == id) {
                        iOrder.deleteOrderByID(it.id!!)
                        listFav.remove(id)
                        _favList.postValue(listFav)
                    }
                }
            }
        }
    }

    fun getDraftOrder() {
        viewModelScope.launch {
            val order = iOrder.getAllOrders(user.email)
            withContext(Dispatchers.Main) {
                order.forEach {
                    if (it.note == Keys.FAV) {
                        if (it.lineItems[0].productId != null)
                            listFav.add(it.lineItems[0].productId!!)
                    } else if (it.note == Keys.CART) {
                        listCart.add(it.lineItems[0].variantId!!)
                    }
                }
                _cartList.postValue(listCart)
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

    fun isLogged():Boolean{
        return iUser.getLoggedInState()
    }
}