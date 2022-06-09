package com.example.mcommerceapp.view.ui.order.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemOrdersBinding
import com.example.mcommerceapp.databinding.ProductCardBinding
import orders.Order
import orders.Orders

class OrderAdapter (var context :Context , var listner : OnClickListener) : RecyclerView.Adapter<OrderAdapter.ViewHolder>(){
    private var orderList: ArrayList<Order> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = orderList[position]
        holder.binding.apply {
            orderNameTxt.text = currentItem.name
            orderDateTxt.text = currentItem.createdAt
            countItemsTxt.text = "${currentItem.lineItems.size} items"
            totalItemsTxt.text = currentItem.currentTotalPrice
        }
        holder.itemView.setOnClickListener {
            listner.onClick(currentItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return orderList.count()
    }

    fun setData(orderList: ArrayList<Order>){
        this.orderList = orderList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemOrdersBinding): RecyclerView.ViewHolder(binding.root)
}