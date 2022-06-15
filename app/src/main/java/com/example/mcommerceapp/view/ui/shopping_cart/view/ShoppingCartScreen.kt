package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.MainActivity
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityShoppingCartScreenBinding
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.payment.view.Payment
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodel
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodelFactory
import draft_orders.DraftOrder

class ShoppingCartScreen : AppCompatActivity(), CartCommunicator {

    private lateinit var binding: ActivityShoppingCartScreenBinding

    private lateinit var cartItemsAdapter: CartItemsAdapter

    private lateinit var cartViewModel: ShoppingCartViewmodel
    private lateinit var cartViewModelFactory: ShoppingCartViewmodelFactory

    private var cartList: ArrayList<DraftOrder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartItemsRecyclerView.setHasFixedSize(true)
        supportActionBar?.hide()

        binding.progressIndicator.visibility = View.VISIBLE

        binding.cartItemsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.cartItemsRecyclerView.layoutManager = linearLayoutManager

        cartItemsAdapter = CartItemsAdapter(arrayListOf(), this, this)
        binding.cartItemsRecyclerView.adapter = cartItemsAdapter
        cartItemsAdapter = CartItemsAdapter(cartList, this, this)
        binding.cartItemsRecyclerView.adapter = cartItemsAdapter

        binding.checkoutBt.setOnClickListener {
            startActivity(Intent(this, Payment::class.java))
        }

        binding.actionBar.backImg.setOnClickListener {
            finish()
        }

        cartViewModelFactory = ShoppingCartViewmodelFactory(
            DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            this
        )

        cartViewModel =
            ViewModelProvider(this, cartViewModelFactory)[ShoppingCartViewmodel::class.java]

        cartViewModel.getUser().observe(this) {
            if (it != null) {
                cartViewModel.getAllDraftOrders(it.userID)
            } else {
                print("No User Found")
            }
        }

        cartViewModel.draftOrderLiveData.observe(this) {
            if (it != null) {
                cartList = it
                binding.progressIndicator.visibility = View.INVISIBLE
                cartItemsAdapter.setOrders(cartList)
                calculateSubTotal()
            }
        }

        cartViewModel.updateLiveData.observe(this) {
            if (it != null) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        cartViewModel.updateDarftOrder(cartList)
    }

    override fun calculateNewSubTotal() {
        calculateSubTotal()
    }

    override fun deleteProductFromCart(index: Int) {
        val obj = cartList[index]
        cartViewModel.deleteProductFromDraftOrder(obj.id!!)
        deleteDraftOrderFromList(obj)
    }

    override fun increaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity++
        cartList[index].lineItems[0].quantity = (newQuantity)
    }

    override fun decreaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity--
        cartList[index].lineItems[0].quantity = (newQuantity)
    }

    fun deleteDraftOrderFromList(draftOrder: DraftOrder) {
        cartList.remove(draftOrder)
        cartItemsAdapter.setOrders(cartList)
    }

    fun calculateSubTotal(){
        var subTotal = 0
        cartList.forEach{ cartItem ->
            subTotal+= (cartItem.lineItems[0].quantity!!.times(cartItem.lineItems[0].price!!.toInt()))
        }
        binding.subTotalValueTx.text = subTotal.toString()
    }
}