package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import android.content.Context
import android.util.Log
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

    private var updateMutableLiveData = MutableLiveData<Boolean>()
    var updateLiveData: LiveData<Boolean> = updateMutableLiveData

    fun getAllDraftOrders(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val orders = iCartRepo.getAllOrders(userId)
            withContext(Dispatchers.Main) {
                draftOrderMutableLiveData.postValue(orders)
            }
        }
    }

    fun updateDarftOrder(draftOrders: ArrayList<DraftOrder>) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(ShoppingCartViewmodel::class.java.name, draftOrders.size.toString())
            Log.e("TAG", "updateDarftOrder: ${draftOrders.size}")
            for (draftOrder in draftOrders) {
                Log.e("TAG2", "updateDarftOrder: ${draftOrder.lineItems[0].quantity}")
                iCartRepo.updateOrder(draftOrder.id.toString(), draftOrder)
            }
            withContext(Dispatchers.Main) {
                updateMutableLiveData.postValue(true)
            }
        }
    }

    fun deleteProductFromDraftOrder(draftOrderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            iCartRepo.deleteOrderByID(draftOrderId)
        }
    }


    fun getUser(): LiveData<User> {
        return iUserRepo.retrieveUserFromFireStore()
    }
}