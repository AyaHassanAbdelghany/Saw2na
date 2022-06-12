package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R

class CartItemsAdapter(private var myContext: Context) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View =
            layoutInflater.inflate(R.layout.item_shopping_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.clipToOutline = true

        var countValue = holder.countTx.text.toString().toInt()
        if (countValue == 1) {
            holder.minusBt.visibility = View.INVISIBLE
        }

        holder.minusBt.setOnClickListener {
            countValue--
            if (countValue <= 1) {
                holder.countTx.text = (countValue).toString()
                holder.value.text = (15 * countValue).toString()
                holder.minusBt.visibility = View.INVISIBLE
            } else {
                holder.countTx.text = (countValue).toString()
                holder.value.text = (15 * countValue).toString()
            }
        }
        holder.plusBt.setOnClickListener {
            /*if (countValue  1) {

            } else {*/
            holder.minusBt.visibility = View.VISIBLE
            countValue++
            holder.countTx.text = (countValue).toString()
            holder.value.text = (15 * countValue).toString()
            //}
        }

    }

    override fun getItemCount(): Int {
        return 10
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_image)
        var minusBt: ImageButton = itemView.findViewById(R.id.minus_bt)
        var plusBt: ImageButton = itemView.findViewById(R.id.plus_bt)
        var countTx: TextView = itemView.findViewById(R.id.item_count_tx)
        var name: TextView = itemView.findViewById(R.id.item_name_tx)
        var value: TextView = itemView.findViewById(R.id.item_value_tx)
    }

}