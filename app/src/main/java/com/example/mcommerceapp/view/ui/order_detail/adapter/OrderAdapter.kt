package com.example.mcommerceapp.view.ui.order_detail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemOrderDetailsBinding
import orders.LineItems

class OrderDetailAdapter(var context: Context, var listner: OnClickListener) :
    RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
    private var orderList: ArrayList<LineItems> = arrayListOf()
    private lateinit var symbol: String
    private var value: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = orderList[position]
        holder.binding.apply {
            nameOrderDetailTxt.text = currentItem.name
            priceOrderDetailTxt.text = "${currentItem.price?.toDouble()?.times(value)} ${symbol}"
            quantityOrderDetail.text = "quantity  ${currentItem.quantity}"
        }
        holder.itemView.setOnClickListener {
            listner.onClick(currentItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return orderList.count()
    }

    fun setData(orderList: ArrayList<LineItems>, symbol: String, value: Double) {
        this.orderList = orderList
        this.symbol = symbol
        this.value = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemOrderDetailsBinding) : RecyclerView.ViewHolder(binding.root)
}