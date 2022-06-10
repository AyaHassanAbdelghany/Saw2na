package com.example.mcommerceapp.view.ui.order

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityOrdersBinding
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.order.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.order.adapter.OrderAdapter

class OrderActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityOrdersBinding
    lateinit var orderVM: OrderViewModel
    lateinit var orderVMFactory: OrderViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderVMFactory = OrderViewModelFactory(
            OrdersRepo.getInstance(OrdersRemoteSource()), UserRepo.getInstance(this)
            )
        orderVM = ViewModelProvider(this, orderVMFactory)[OrderViewModel::class.java]

        orderVM.getAllOrders()
        val orderAdapter = OrderAdapter(this, this)
        orderVM.orders.observe(this) {
            Log.d("List", it.toString())
            orderAdapter.setData(it)
            binding.recycleViewOrder.adapter = orderAdapter
        }

    }

    override fun onClick(id: String) {
        TODO("Not yet implemented")
    }
}