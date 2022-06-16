package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        // supportActionBar?.hide()

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
            val myTitleList = retrieveProductsTitle(cartList)
            if (!myTitleList.isNullOrEmpty()) {
                var intent = Intent(this, Payment::class.java)
                intent.putStringArrayListExtra("names_list", myTitleList)
                intent.putExtra("total_value", binding.totalValueTx.text.toString().toDouble())
                startActivity(intent)
            }
        }


        cartViewModelFactory = ShoppingCartViewmodelFactory(
            DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            this
        )

        cartViewModel =
            ViewModelProvider(this, cartViewModelFactory)[ShoppingCartViewmodel::
            class.java]

        cartViewModel.getUser().observe(this) {
            if (it != null) {
                cartViewModel.getAllDraftOrders(it.userID)
            } else {
                print("No User Found")
            }
        }

        cartViewModel.draftOrderLiveData.observe(this)
        {
            if (it != null) {
                cartList = it
                binding.progressIndicator.visibility = View.INVISIBLE
                cartItemsAdapter.setOrders(cartList)
                calculateTotalAmountOfMoney()
            }
        }

        cartViewModel.updateLiveData.observe(this)
        {
            if (it != null) {
                finish()
            }
        }
        binding.actionBar.backImg.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        cartViewModel.updateDarftOrder(cartList)
    }

    override fun calculateNewSubTotal() {
        calculateTotalAmountOfMoney()
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
        calculateTotalAmountOfMoney()
    }

    override fun decreaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity--
        cartList[index].lineItems[0].quantity = (newQuantity)
        calculateTotalAmountOfMoney()
    }

    private fun deleteDraftOrderFromList(draftOrder: DraftOrder) {
        cartList.remove(draftOrder)
        cartItemsAdapter.setOrders(cartList)
        calculateTotalAmountOfMoney()
    }

    private fun calculateTotalAmountOfMoney() {
        val subTotal = calculateSubTotal()
        val total = calculateTotal(subTotal, 0.0, 0.0)
        binding.subTotalValueTx.text = subTotal.toString()
        binding.discountValueTx.text = "0.0"
        binding.shippingValueTx.text = "0.0"
        binding.totalValueTx.text = total.toString()
    }

    private fun calculateSubTotal(): Double {
        var subTotal = 0.0
        cartList.forEach { cartItem ->
            subTotal += (cartItem.lineItems[0].quantity!!.toInt()
                .times(cartItem.lineItems[0].price!!.toDouble()))
        }
        return subTotal
    }

    private fun calculateTotal(subTotal: Double, discount: Double, shipping: Double): Double {
        return (subTotal + discount + shipping)
    }

    private fun retrieveProductsTitle(list: ArrayList<DraftOrder>): ArrayList<String> {
        var titleList: ArrayList<String> = ArrayList()
        list.forEach {
            titleList.add(it.lineItems[0].name.toString())
        }
        return titleList
    }
}