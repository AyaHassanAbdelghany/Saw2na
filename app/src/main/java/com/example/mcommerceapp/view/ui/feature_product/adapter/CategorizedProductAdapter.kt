package com.example.mcommerceapp.view.ui.feature_product.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.databinding.ProductCardBinding
import com.example.mcommerceapp.pojo.products.Products

class CategorizedProductAdapter(var context: Context, var listner: OnClickListner) :
    RecyclerView.Adapter<CategorizedProductAdapter.ViewHolder>() {
    var productList: List<Products> = mutableListOf()
    private lateinit var symbol: String
    private var value: Double = 0.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = productList[position]
        holder.binding.apply {
            productNameTxt.text = currentItem.title?.split("|")?.get(1)?.trim()

            Glide.with(context)
                .load(currentItem.image?.src)
                .into(productImage)

            productPriceTxt.text =
                String.format("%.2f", currentItem.variants[0].price?.toDouble()?.times(value))

            productPriceCurrencyTxt.text = "${symbol}"
        }
        holder.itemView.setOnClickListener(View.OnClickListener
        {
            Log.e("productid", currentItem.id.toString())
            Log.e("vendorTitle", currentItem.id.toString())
            listner.onClick(currentItem.id.toString())
        })
    }

    override fun getItemCount(): Int {
        return productList.count()
    }

    fun setData(productList: ArrayList<Products>, symbol: String, value: Double) {
        this.productList = productList
        this.symbol = symbol
        this.value = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root)
}