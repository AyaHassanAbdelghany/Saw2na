package com.example.mcommerceapp.view.ui.favorite_product.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.R
import draft_orders.DraftOrder

class FavoriteItemsAdapter(
    private var myContext: Context,
    private var myList: ArrayList<DraftOrder>,
    private var communicator: FavoriteScreenCommunicator
) :
    RecyclerView.Adapter<FavoriteItemsAdapter.ViewHolder>() {

    private lateinit var symbol: String
    private var value: Double = 0.0

    fun setFavoriteProducts(myList: ArrayList<DraftOrder>, symbol: String, value: Double) {
        this.myList = myList
        this.symbol = symbol
        this.value = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View =
            layoutInflater.inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productImage.clipToOutline = true
        holder.productName.text = myList[position].lineItems[0].variantTitle
        holder.productValue.text =
            "${myList[position].lineItems[0].price?.toDouble()?.times(value)}"
        holder.productsCurrency.text = symbol
        holder.productDeleteBt.setOnClickListener {
            communicator.performDeleteProduct(myList[position])
        }

        Glide.with(myContext)
            .load(myList[position].noteAttributes[0].value)
            .into(holder.productImage)
    }

    override fun getItemCount(): Int {
        return myList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.product_image)
        var productName: TextView = itemView.findViewById(R.id.product_name_tx)
        var productValue: TextView = itemView.findViewById(R.id.product_value_tx)
        var productsCurrency: TextView = itemView.findViewById(R.id.product_cureency_tx)
        var productDeleteBt: ImageButton = itemView.findViewById(R.id.product_delete_bt)
    }
}