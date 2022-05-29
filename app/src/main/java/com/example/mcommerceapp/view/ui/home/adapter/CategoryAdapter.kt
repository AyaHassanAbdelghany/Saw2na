package com.example.mcommerceapp.view.ui.home.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemListCatogeryBinding

class CategoryAdapter  : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private  var category : List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListCatogeryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = category[position]

        holder.binding.apply {
            catogeryNameText.text = currentItem
        }
    }

    override fun getItemCount(): Int {
        return category.count()
    }

    fun setData(category: List<String>){
        this.category = category
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListCatogeryBinding): RecyclerView.ViewHolder(binding.root)
}