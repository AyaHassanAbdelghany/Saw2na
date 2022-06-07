package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.view.ui.payment.view.Payment

class ShoppingCartScreen : AppCompatActivity() {
    private lateinit var cartItemsRecyclerView: RecyclerView
    private lateinit var cartItemsAdapter: CartItemsAdapter
    private lateinit var checkoutBt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart_screen)
        supportActionBar?.hide()

        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler_view)
        cartItemsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        cartItemsRecyclerView.layoutManager = linearLayoutManager

        cartItemsAdapter = CartItemsAdapter(this)
        cartItemsRecyclerView.adapter = cartItemsAdapter

        checkoutBt = findViewById(R.id.checkout_bt)
        checkoutBt.setOnClickListener {
            startActivity(Intent(this, Payment::class.java))
        }
    }
}