package com.example.mcommerceapp.view.ui.order_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.AcitivityOrderDetailsBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.orders.OrdersRepo
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.view.ui.order_detail.adapter.OrderDetailAdapter
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity() {

    lateinit var binding: AcitivityOrderDetailsBinding
    lateinit var orderDetailVM: OrderDetailViewModel
    lateinit var orderDetailVMFactory: OrderDetailViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("ORDER_ID")

        orderDetailVMFactory = OrderDetailViewModelFactory(
            OrdersRepo.getInstance(OrdersRemoteSource()), CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(), this
            )
        )
        orderDetailVM =
            ViewModelProvider(this, orderDetailVMFactory)[OrderDetailViewModel::class.java]

        val orderDetailAdapter = OrderDetailAdapter(this)

        orderDetailVM.orders.observe(this) {
            binding.totalOrderTxt.text = "${
                it.totalPrice?.toDouble()?.times(orderDetailVM.currencyValue)
            } ${orderDetailVM.currencySymbol}"
            binding.countItemsTxt.text = "${
                it.currentSubtotalPrice?.toDouble()?.times(orderDetailVM.currencyValue)
            } ${orderDetailVM.currencySymbol}"
            binding.priceItemsTxt.text = it.totalShippingPriceSet?.shopMoney?.amount

            binding.addressItemsTxt.text = it.customer?.defaultAddress?.address1
            val spf = SimpleDateFormat("yyyy-MM-dd")
            val createdAt = spf.format(spf.parse(it.createdAt))
            binding.dateItemsTxt.text = createdAt

            orderDetailAdapter.setData(
                it.lineItems,
                orderDetailVM.currencySymbol,
                orderDetailVM.currencyValue
            )
            binding.recycleViewProduct.adapter = orderDetailAdapter
        }
        binding.toolbar.backImg.setOnClickListener {
            finish()
        }
        MyConnectivityManager.state.observe(this) {

            if (it) {
                orderDetailVM.getOrder(id.toString())
                binding.networkLayout.noNetworkLayout.visibility = View.INVISIBLE
                binding.mainLayout.visibility = View.VISIBLE

            } else {
                binding.networkLayout.noNetworkLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.INVISIBLE

            }
        }
    }

}