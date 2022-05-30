package com.example.mcommerceapp.view.ui.shopping_cart.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R

class ShoppingCartScreen : AppCompatActivity() {
    private lateinit var cartItemsRecyclerView: RecyclerView
    private lateinit var cartItemsAdapter: CartItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart_screen)


        cartItemsRecyclerView = findViewById(R.id.cart_items_recycler_view)
        cartItemsRecyclerView.setHasFixedSize(true)
        val timeLinearLayoutManager = LinearLayoutManager(this)
        timeLinearLayoutManager.orientation = RecyclerView.VERTICAL
        cartItemsRecyclerView.layoutManager = timeLinearLayoutManager

        cartItemsAdapter = CartItemsAdapter(this)
        cartItemsRecyclerView.adapter = cartItemsAdapter
    }
}