package com.example.mcommerceapp.view.ui.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityOrdersBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
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
                RemoteSource(), this
            )
        )

        orderVM = ViewModelProvider(this, orderVMFactory)[OrderViewModel::class.java]

        orderVM.getAllOrders()
        val orderAdapter = OrderAdapter(this, this)
        orderVM.orders.observe(this) {
            Log.d("List", it.toString())
            Log.d("Currency", "Symbol" + orderVM.currencySymbol + "Value" + orderVM.currencyValue)
            orderAdapter.setData(it, orderVM.currencySymbol, orderVM.currencyValue)
            binding.recycleViewOrder.adapter = orderAdapter
        }

    }

    override fun onClick(id: String) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra("ORDER_ID", id)
        startActivity(intent)
    }
}