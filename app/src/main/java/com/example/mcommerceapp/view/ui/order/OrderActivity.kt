package com.example.mcommerceapp.view.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityOrdersBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.view.ui.order.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.order.adapter.OrderAdapter
import com.example.mcommerceapp.view.ui.order_detail.OrderDetailActivity

class OrderActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityOrdersBinding
    lateinit var orderVM: OrderViewModel
    lateinit var orderVMFactory: OrderViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderVMFactory = OrderViewModelFactory(

            OrdersRepo.getInstance(OrdersRemoteSource()),
            UserRepo.getInstance(this),
            CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(), this
            )
        )

        orderVM = ViewModelProvider(this, orderVMFactory)[OrderViewModel::class.java]

        val orderAdapter = OrderAdapter(this, this)
        orderVM.orders.observe(this) {
           orderAdapter.setData(it, orderVM.currencySymbol, orderVM.currencyValue)
            binding.recycleViewOrder.adapter = orderAdapter
        }

        binding.toolbar.backImg.setOnClickListener {
            finish()
        }

        MyConnectivityManager.state.observe(this) {

            if (it) {
                orderVM.getAllOrders()
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
    }

    override fun onClick(id: String) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra("ORDER_ID", id)
        startActivity(intent)
    }

}