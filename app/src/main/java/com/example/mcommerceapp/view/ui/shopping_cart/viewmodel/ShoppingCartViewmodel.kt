package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.draft_orders.interfaces.ShoppingCartRepoInterface
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.GetUserCartRepo
import com.example.mcommerceapp.pojo.user.User
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingCartViewmodel(
    private val iCartRepo: ShoppingCartRepoInterface,
    private val iUserRepo: GetUserCartRepo,
    private val myContext: Context
) : ViewModel() {
    private var draftOrderMutableLiveData = MutableLiveData<ArrayList<DraftOrder>>()
    var draftOrderLiveData: LiveData<ArrayList<DraftOrder>> = draftOrderMutableLiveData

    fun getAllDraftOrders(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val orders = iCartRepo.getAllOrders(userId)
//            withContext(Dispatchers.Main) {
//                favProductsMutableLiveData.postValue(products)
//            }
        }
    }

    fun updateDarftOrder(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            val value = iCartRepo.updateOrder("",)
        }
    }


    fun getUser(): LiveData<User> {
        return iUserRepo.retrieveUserFromFireStore()
    }
}