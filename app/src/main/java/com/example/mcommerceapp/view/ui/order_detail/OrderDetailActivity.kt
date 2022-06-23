package com.example.mcommerceapp.view.ui.order_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.AcitivityOrderDetailsBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.view.ui.order_detail.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.order_detail.adapter.OrderDetailAdapter
import java.text.SimpleDateFormat

class OrderDetailActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: AcitivityOrderDetailsBinding
    lateinit var orderDetailVM: OrderDetailViewModel
    lateinit var orderDetailVMFactory: OrderDetailViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("ORDER_ID")
        Log.d("ooooooooo", id.toString())

        orderDetailVMFactory = OrderDetailViewModelFactory(
            OrdersRepo.getInstance(OrdersRemoteSource()), CurrencyRepo.getInstance(
                RemoteSource(), this
            )
        )
        orderDetailVM =
            ViewModelProvider(this, orderDetailVMFactory)[OrderDetailViewModel::class.java]

        orderDetailVM.getOrder(id.toString())
        val orderDetailAdapter = OrderDetailAdapter(this, this)

        orderDetailVM.orders.observe(this) {
            binding.totalOrderTxt.text = "${
                it.totalPrice?.toDouble()?.times(orderDetailVM.currencyValue)
            } ${orderDetailVM.currencySymbol}"
            binding.countItemsTxt.text = "${
                it.currentSubtotalPrice?.toDouble()?.times(orderDetailVM.currencyValue)
            } ${orderDetailVM.currencySymbol}"
            binding.priceItemsTxt.text = it.totalShippingPriceSet?.shopMoney?.amount

            binding.phoneItemsTxt.text = it.phone
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
    }

    override fun onClick(id: String) {
//        val intent = Intent(this, ProductDetail::class.java)
//        intent.putExtra("PRODUCTS_ID", id)
//        startActivity(intent)
    }
}