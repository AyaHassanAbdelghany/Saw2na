package com.example.mcommerceapp.view.ui.shopping_cart.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceadminapp.model.shopify_repository.coupon.ICouponRepo
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceapp.model.currency_repository.interfaces.StoredCurrency
import com.example.mcommerceapp.model.draft_orders_repository.interfaces.ShoppingCartRepoInterface
import com.example.mcommerceapp.model.user_repository.user_repo_interfaces.GetUserCartRepo
import com.example.mcommerceapp.pojo.user.User
import draft_orders.DraftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ShoppingCartViewmodel(
    private val iCartRepo: ShoppingCartRepoInterface,
    private val iUserRepo: GetUserCartRepo,
    private val iCouponsRepo: ICouponRepo,
    private val iCurrencyRepo: StoredCurrency
) : ViewModel() {
    private var draftOrderMutableLiveData = MutableLiveData<ArrayList<DraftOrder>>()
    var draftOrderLiveData: LiveData<ArrayList<DraftOrder>> = draftOrderMutableLiveData

    private var updateMutableLiveData = MutableLiveData<Boolean>()
    var updateLiveData: LiveData<Boolean> = updateMutableLiveData

    private val _allDiscountCode = MutableLiveData<ArrayList<DiscountCodes>>()
    var allDiscountCode: LiveData<ArrayList<DiscountCodes>> = _allDiscountCode

    private var discountList = arrayListOf<DiscountCodes>()

    val symbol = iCurrencyRepo.getCurrencySymbol()
    val value = iCurrencyRepo.getCurrencyValue()

    private lateinit var end: String

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
            for (draftOrder in draftOrders) {
                iCartRepo.updateOrder(draftOrder.id.toString(), draftOrder)
            }
            withContext(Dispatchers.Main) {
                updateMutableLiveData.postValue(true)
            }
        }
    }

    fun deleteAllOrders(draftOrders: ArrayList<DraftOrder>, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            draftOrders.forEach {
                iCartRepo.deleteOrderByID(it.id!!)
            }
            withContext(Dispatchers.Main) {
                getAllDraftOrders(userId)
            }
        }
    }

    fun deleteProductFromDraftOrder(draftOrderId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            iCartRepo.deleteOrderByID(draftOrderId)
        }
    }


    fun getUser(): LiveData<User> {
        return iUserRepo.retrieveUserFromFireStore()
    }

    fun getAllDiscount() {
        viewModelScope.launch {
            val priceRules = iCouponsRepo.getAllPriceRules()
            Log.d("priceRuleAct", priceRules.size.toString())

            priceRules.forEach { rules ->
//                end = SimpleDateFormat("dd-MM-yyyy").format(rules.endsAt)
                println("Testing: ${rules.startsAt}")
                val check = checkCurrentTimeIsBetweenGivenString(formatDate(rules.startsAt!!), formatDate(rules.endsAt!!))
                Log.d("Testing", check.toString())

                if (check) {
                    val discount = iCouponsRepo.getAllDiscountCode(rules.id.toString())
                    Log.d("TestingDiscount", discount.toString())
                    discount.forEach {
                        println("code: ${it.code}")
                        it.createdAt = rules.value
                        discountList.add(it)
                    }
                } else {
                    Log.d("priceCheck", "Finish..")
                }
            }
            withContext(Dispatchers.Main) {
                Log.d("discountList", discountList.size.toString())
                _allDiscountCode.postValue(discountList)
            }
        }

    }

    private fun formatDate(date: String): String {
        val odt = OffsetDateTime.parse(date)
        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        println("ISA : ${dtf.format(odt)}")
        return dtf.format(odt)
    }

    private fun checkCurrentTimeIsBetweenGivenString(s1: String, s2: String): Boolean {

        println("Testing: $s1")
        println("Testing: $s2")
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

        val date1: Date = simpleDateFormat.parse(s1)

        val date2: Date = simpleDateFormat.parse(s2)

        println("TestingStart: $date1")
        println("TestingEnd: $date2")
        val d = Date()

        val s3 = simpleDateFormat.format(d)

        val date3 = simpleDateFormat.parse(s3)
        println("TestingNow: $date3")
        println("TestingCheck: ${date3 >= date1 && date3 <= date2}")
        return date3 >= date1 && date3 <= date2
    }
}