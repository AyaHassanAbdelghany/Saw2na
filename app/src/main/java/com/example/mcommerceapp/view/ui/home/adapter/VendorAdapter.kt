package com.example.mcommerceapp.view.ui.home.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.databinding.ItemListVendorBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class VendorAdapter (var context :Context , var listner : OnClickListner) : RecyclerView.Adapter<VendorAdapter.ViewHolder>(){

    private  var vendor : HashSet<SmartCollections> = hashSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListVendorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = vendor.elementAt(position)
        holder.binding.apply {
            brandNameText.text = currentItem.title

            Glide.with(context)
                .load(currentItem.image?.src)
                .into(brandImage)
        }
        holder.binding.brandImage.setOnClickListener(View.OnClickListener
        {
            listner!!.onClick(currentItem.title,Keys.VENDOR)
        })
    }

    override fun getItemCount(): Int {
        return vendor.count()
    }

    fun setData(vendor: HashSet<SmartCollections>){
        this.vendor = vendor
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListVendorBinding): RecyclerView.ViewHolder(binding.root)
}