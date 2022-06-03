package com.example.mcommerceapp.view.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.R

class AdvAdapter(val viewPager: ViewPager2, private val advImages: ArrayList<Int>): RecyclerView.Adapter<AdvAdapter.SliderImageHolder>() {

    inner class SliderImageHolder(var view: View): RecyclerView.ViewHolder(view){
        val advImage = view.findViewById<ImageView>(R.id.advImage)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_advs, parent, false)
        return SliderImageHolder(view)
    }

    override fun onBindViewHolder(holder: SliderImageHolder, position: Int) {
        val listAdv = advImages[position]
        holder.advImage.setImageResource(listAdv)
        if(position == advImages.size -2){
            viewPager.post(run)
        }
    }
    private val run = Runnable {
        advImages.addAll(advImages)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = advImages.size
}