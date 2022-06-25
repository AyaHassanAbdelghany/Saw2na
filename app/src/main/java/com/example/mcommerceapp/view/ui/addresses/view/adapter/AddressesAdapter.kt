package com.example.mcommerceapp.view.ui.addresses.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ItemAddressBinding
import com.example.mcommerceapp.pojo.customers.Addresses


class AddressesAdapter(var context: Context, private var listener: AddressesCommunicator) :
    RecyclerView.Adapter<AddressesAdapter.ViewHolder>() {
    private var orderList: ArrayList<Addresses> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = orderList[position]
        holder.binding.apply {
            cityTextView.text = "${currentItem.address1} , ${currentItem.city}"
            zipCodeTextView.text = currentItem.zip
            countryTextView.text = currentItem.country
        }
        if (currentItem.default == true) {
            holder.itemView.setBackgroundResource(R.drawable.card_border_colored)
        }
        holder.binding.deleteAddress.setOnClickListener {
            if (currentItem.default == true)
                Toast.makeText(
                    context,
                    context.getString(R.string.Cannot_delete_your_default_address),
                    Toast.LENGTH_LONG
                ).show()
            else listener.deleteAddress(currentItem.id.toString())
        }
        holder.itemView.setOnClickListener {
            listener.setDefaultAddress(currentItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return orderList.count()
    }

    fun setData(orderList: ArrayList<Addresses>) {
        this.orderList = orderList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root)

}