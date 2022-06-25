package com.example.mcommerceapp.view.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.shopify_repository.currency.interfaces.StoredCurrency
import com.example.mcommerceapp.model.shopify_repository.product.ICollectionsRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.product.IProductsRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private var iCollections: ICollectionsRepo,
    private var iProducts: IProductsRepo,
    private var iCurrency: StoredCurrency,
    private val iUser: UserRepo
) : ViewModel() {

    private val _vendors: MutableLiveData<HashSet<SmartCollections>> = ProductRepo.vendors
    var vendors: LiveData<HashSet<SmartCollections>> = _vendors


    private val _allProducts: MutableLiveData<ArrayList<Products>> = ProductRepo.allProducts
    var allProducts: LiveData<ArrayList<Products>> = _allProducts


    fun getProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            iCollections.getSmartCollections()
            iCollections.getSubCollection(Keys.PRODUCT_TYPE)
            iProducts.getAllProducts()
        }
    }

    fun getCurrencySymbol(): String {
        return iCurrency.getCurrencySymbol()
    }

    fun getCurrencyValue(): Double {
        return iCurrency.getCurrencyValue()
    }

    fun isLogged(): Boolean{
        return iUser.getLoggedInState()
    }

}