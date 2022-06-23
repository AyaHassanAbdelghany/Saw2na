package com.example.mcommerceapp.view.ui.order.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemOrdersBinding
import orders.Order

class OrderAdapter(var context: Context, var listner: OnClickListener) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var orderList: ArrayList<Order> = arrayListOf()
    private lateinit var symbol: String
    private var value: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = orderList[position]
        holder.binding.apply {
            orderNameTxt.text = currentItem.name
            orderDateTxt.text = currentItem.createdAt
            countItemsTxt.text = "${currentItem.lineItems.size} items"
            totalItemsTxt.text =
                "${currentItem.currentTotalPrice?.toDouble()?.times(value)} ${symbol}"
        }
        holder.itemView.setOnClickListener {
            listner.onClick(currentItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return orderList.count()
    }

    fun setData(orderList: ArrayList<Order>, symbol: String, value: Double) {
        this.orderList = orderList
        this.symbol = symbol
        this.value = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root)
}