package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceapp.databinding.ItemCouponBinding

class CouponsAdapter(var context: Context, var listner: CartCommunicator) :
    RecyclerView.Adapter<CouponsAdapter.ViewHolder>() {

    private var discount: ArrayList<DiscountCodes> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCouponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = discount[position]
        holder.binding.apply {
            couponText.text = currentItem.code
            descText.text = "You have ${currentItem.createdAt?.drop(1)} EGP."
        }
        holder.binding.copyImage.setOnClickListener {
            listner.onClick(code = currentItem.code!!, limit = currentItem.createdAt!!)
        }
    }

    override fun getItemCount(): Int {
        return discount.count()
    }

    fun setData(discount: ArrayList<DiscountCodes>) {
        this.discount = discount
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root)
}