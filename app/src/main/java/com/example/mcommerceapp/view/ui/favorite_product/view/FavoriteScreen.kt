package com.example.mcommerceapp.view.ui.favorite_product.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityFavoriteScreenBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.draft_orders.DraftOrdersRepo
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.network.MyConnectivityManager
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

        binding.numberOfItemsTx.text = getString(R.string.you_have_no_item)

        favoriteViewModelFactory = FavoriteViewModelFactory(
            RoomRepo.getInstance(
                LocalSource.getInstance(this),
                this
            ), DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            CurrencyRepo.getInstance(ProductRemoteSource.getInstance(), this)
        )

        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory)[FavoriteViewModel::class.java]

        // favoriteViewModel.getDraftOrder()


        favoriteItemsAdapter = FavoriteItemsAdapter(this, favProductsList, this)
        binding.favItemsRecyclerView.adapter = favoriteItemsAdapter

        favoriteViewModel.favList.observe(this) {
            favProductsList = it
            favoriteItemsAdapter.setFavoriteProducts(
                favProductsList,
                favoriteViewModel.symbol,
                favoriteViewModel.value
            )
            if (favProductsList.size > 0)
                binding.numberOfItemsTx.text =
                    "${getString(R.string.you_have)} ${favProductsList.size} ${getString(R.string.itemi_number)}"
            else binding.numberOfItemsTx.text = getString(R.string.you_have_no_item)
        }

        binding.favItemsRecyclerView.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.favItemsRecyclerView.layoutManager = gridLayoutManager

        binding.actionBar.backImg.setOnClickListener {
            finish()
        }

        MyConnectivityManager.state.observe(this) {

            if (it) {
                favoriteViewModel.getDraftOrder()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
    }

    override fun performDeleteProduct(product: DraftOrder) {
        // favoriteViewModel.deleteFavoriteProduct(product)
        favoriteViewModel.deleteOrder(product.lineItems[0].productId!!)
        favProductsList.remove(product)
        favoriteItemsAdapter.setFavoriteProducts(
            favProductsList,
            favoriteViewModel.symbol,
            favoriteViewModel.value
        )
        if (favProductsList.size > 0)
            binding.numberOfItemsTx.text =
                "${getString(R.string.you_have)} ${favProductsList.size} ${getString(R.string.itemi_number)}"
        else binding.numberOfItemsTx.text = getString(R.string.you_have_no_item)

    }
}