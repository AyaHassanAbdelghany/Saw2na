package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.payment.view.Payment
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodel
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodelFactory
import draft_orders.DraftOrder

class ShoppingCartScreen : AppCompatActivity(), CartCommunicator {
    private lateinit var cartItemsRecyclerView: RecyclerView
    private lateinit var cartItemsAdapter: CartItemsAdapter
    private lateinit var checkoutBt: Button

    private lateinit var subTotalTx: TextView
    private lateinit var discountTx: TextView
    private lateinit var shippingTx: TextView
    private lateinit var totalTx: TextView

    private lateinit var cartViewModel: ShoppingCartViewmodel
    private lateinit var cartViewModelFactory: ShoppingCartViewmodelFactory

    private var cartList: ArrayList<DraftOrder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart_screen)
        supportActionBar?.hide()

        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler_view)
        cartItemsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        cartItemsRecyclerView.layoutManager = linearLayoutManager

        cartItemsAdapter = CartItemsAdapter(cartList, this)
        cartItemsRecyclerView.adapter = cartItemsAdapter

        checkoutBt = findViewById(R.id.checkout_bt)
        checkoutBt.setOnClickListener {
            startActivity(Intent(this, Payment::class.java))
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
            cartItemsAdapter.setOrders(it)
//            it?.forEach {
//                println(it.id)
//
//            }
        }
    }

    override fun calculateNewSubTotal(value: Double) {

    }
}