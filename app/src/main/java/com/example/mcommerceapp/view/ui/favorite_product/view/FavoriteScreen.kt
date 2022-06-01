package com.example.mcommerceapp.view.ui.favorite_product.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import com.example.mcommerceapp.view.ui.favorite_product.viewmodel.FavoriteViewModel
import com.example.mcommerceapp.view.ui.favorite_product.viewmodel.FavoriteViewModelFactory


class FavoriteScreen : AppCompatActivity(), FavoriteScreenCommunicator {
    private lateinit var favItemsRecyclerView: RecyclerView
    private lateinit var favoriteItemsAdapter: FavoriteItemsAdapter

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private var favProductsList: List<FavProducts> = mutableListOf()

    private lateinit var itemCountsTx: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_screen)

        itemCountsTx = findViewById(R.id.number_of_items_tx)
        itemCountsTx.text = "You have no item in your favorite"

        favoriteViewModelFactory = FavoriteViewModelFactory(
            RoomRepo.getInstance(
                LocalSource.getInstance(this),
                this
            ), this
        )

        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavoriteProducts()
        favoriteViewModel.favProductsLiveData.observe(this) {
            if (it != null) {
                favoriteItemsAdapter.setFavoriteProducts(it)
                favoriteItemsAdapter.notifyDataSetChanged()
                itemCountsTx.text = "You have ${it.count()} items in your favorite"
            }
        }

        ////for test
        favoriteViewModel.insertFavoriteProduct(FavProducts("12", "Hh", 200.0, "Karam"))

        favItemsRecyclerView = findViewById(R.id.fav_items_recycler_view)
        favItemsRecyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        favItemsRecyclerView.layoutManager = gridLayoutManager

        favoriteItemsAdapter = FavoriteItemsAdapter(this, favProductsList, this)
        favItemsRecyclerView.adapter = favoriteItemsAdapter
    }

    override fun performDeleteProduct(product: FavProducts) {
        favoriteViewModel.deleteFavoriteProduct(product)
    }
}