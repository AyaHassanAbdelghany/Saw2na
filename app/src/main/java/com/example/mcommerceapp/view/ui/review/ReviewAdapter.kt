package com.example.mcommerceapp.view.ui.review

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.databinding.CardReviewBinding
import com.example.mcommerceapp.databinding.ProductCardBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.products.Review
import com.example.mcommerceapp.view.ui.home.adapter.OnClickListner

class ReviewAdapter () :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    var reviewList: List<Review> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = reviewList[position]
        holder.binding.apply {
            reviewerNameTxt.text = currentItem.name
            reviewerDateTxt.text = currentItem.date
            reviewerDescTxt.text = currentItem.desc

        }

    }

    override fun getItemCount(): Int = reviewList.count()


    fun setData(reviewList: List<Review>) {
        this.reviewList = reviewList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: CardReviewBinding) : RecyclerView.ViewHolder(binding.root)
}