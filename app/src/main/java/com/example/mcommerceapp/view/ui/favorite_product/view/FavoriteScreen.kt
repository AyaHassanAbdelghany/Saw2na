package com.example.mcommerceapp.view.ui.favorite_product.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mcommerceapp.databinding.ActivityFavoriteScreenBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.favorite_product.viewmodel.FavoriteViewModel
import com.example.mcommerceapp.view.ui.favorite_product.viewmodel.FavoriteViewModelFactory
import draft_orders.DraftOrder


class FavoriteScreen : AppCompatActivity(), FavoriteScreenCommunicator {

    private lateinit var favoriteItemsAdapter: FavoriteItemsAdapter
    private lateinit var binding: ActivityFavoriteScreenBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private var favProductsList: ArrayList<DraftOrder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberOfItemsTx.text = "You have no item in your favorite"

        favoriteViewModelFactory = FavoriteViewModelFactory(
            RoomRepo.getInstance(
                LocalSource.getInstance(this),
                this
            ), DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            CurrencyRepo.getInstance(RemoteSource(), this)
        )

        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.getDraftOrder()
//        favoriteViewModel.getAllFavoriteProducts()
//        favoriteViewModel.favProductsLiveData.observe(this) {
//            if (it != null) {
//                favoriteItemsAdapter.setFavoriteProducts(it)
//                favoriteItemsAdapter.notifyDataSetChanged()
//                binding.numberOfItemsTx.text = "You have ${it.count()} items in your favorite"
//            }
//        }

        favoriteItemsAdapter = FavoriteItemsAdapter(this, favProductsList, this)
        binding.favItemsRecyclerView.adapter = favoriteItemsAdapter

        favoriteViewModel.favList.observe(this) {
            favoriteItemsAdapter.setFavoriteProducts(it, favoriteViewModel.symbol, favoriteViewModel.value)
        }

        binding.favItemsRecyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.favItemsRecyclerView.layoutManager = gridLayoutManager

        binding.actionBar.backImg.setOnClickListener {
            finish()
        }

    }

    override fun performDeleteProduct(product: DraftOrder) {
        // favoriteViewModel.deleteFavoriteProduct(product)
        favoriteViewModel.deleteOrder(product.lineItems[0].productId!!)
        favProductsList.remove(product)
        favoriteItemsAdapter.setFavoriteProducts(favProductsList, favoriteViewModel.symbol, favoriteViewModel.value)
    }
}