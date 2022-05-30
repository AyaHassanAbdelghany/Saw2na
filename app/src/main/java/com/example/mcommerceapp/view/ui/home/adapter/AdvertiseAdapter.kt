package com.example.mcommerceapp.view.ui.home.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemListAdvertiseBinding

class AdvertiseAdapter : RecyclerView.Adapter<AdvertiseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAdvertiseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {

        }
    }

    override fun getItemCount(): Int {
        return 0
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(courses: List<Courses>){
//        this.courses = courses
//        notifyDataSetChanged()
//    }

    class ViewHolder(val binding: ItemListAdvertiseBinding): RecyclerView.ViewHolder(binding.root)
    }