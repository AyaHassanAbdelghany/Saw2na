package com.example.mcommerceapp.view.ui.home.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.databinding.ItemListVendorBinding

class VendorAdapter (var context :Context) : RecyclerView.Adapter<VendorAdapter.ViewHolder>(){

    private  var vendor : MutableMap<String,String> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListVendorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = vendor.keys
        holder.binding.apply {
            brandNameText.text = currentItem.elementAt(position)
            Glide.with(context)
                .load(vendor.getValue(currentItem.elementAt(position)))
                .into(brandImage)
        }
    }

    override fun getItemCount(): Int {
        return vendor.count()
    }

    fun setData(vendor: MutableMap<String,String>){
        this.vendor = vendor
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListVendorBinding): RecyclerView.ViewHolder(binding.root)
}