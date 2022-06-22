package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.R
import draft_orders.DraftOrder

class CartItemsAdapter(
    private var cartList: ArrayList<DraftOrder>,
    private var communicator: CartCommunicator,
    private var myContext: Context
) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    private lateinit var symbol: String
    private var value: Double = 0.0

    fun setOrders(cartList: ArrayList<DraftOrder>, symbol: String, value: Double) {
        this.cartList = cartList
        this.symbol = symbol
        this.value = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View =
            layoutInflater.inflate(R.layout.item_shopping_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = cartList[position].lineItems[0].name.toString()
        holder.image.clipToOutline = true
        Glide.with(myContext)
            .load(cartList[position].noteAttributes[0].value)
            .into(holder.image)

        var quantity = cartList[position].lineItems[0].quantity!!
        var price = cartList[position].lineItems[0].price.toString().toDouble()
        holder.currencyTxt.text = symbol

        holder.countTx.text = quantity.toString()
        holder.value.text = String.format("%.2f", price * quantity * value)

        if (quantity == 1) {
            holder.minusBt.visibility = View.INVISIBLE
        } else if (quantity == 6) {
            holder.plusBt.visibility = View.INVISIBLE
        }

        holder.minusBt.setOnClickListener {
            if (quantity >= 2) {
                quantity--
                communicator.decreaseUpdateInList(position)
            }
            if (quantity == 1) {
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity * value)
                holder.minusBt.visibility = View.INVISIBLE
            } else {
                holder.plusBt.visibility = View.VISIBLE
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity * value)
            }
        }

        holder.plusBt.setOnClickListener {
            if (quantity <= 5) {
                quantity++
                communicator.increaseUpdateInList(position)
            }

            if (quantity == 6) {
                holder.plusBt.visibility = View.INVISIBLE
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity)
                Toast.makeText(myContext, "max quantity in one order", Toast.LENGTH_SHORT).show()
            } else {
                holder.minusBt.visibility = View.VISIBLE
                holder.countTx.text = (quantity).toString()
                holder.value.text = String.format("%.2f", price * quantity * value)
            }
        }

        holder.deleteBt.setOnClickListener {
            communicator.deleteProductFromCart(position)
        }

    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_image)
        var minusBt: ImageButton = itemView.findViewById(R.id.minus_bt)
        var plusBt: ImageButton = itemView.findViewById(R.id.plus_bt)
        var countTx: TextView = itemView.findViewById(R.id.item_count_tx)
        var name: TextView = itemView.findViewById(R.id.item_name_tx)
        var value: TextView = itemView.findViewById(R.id.item_value_tx)
        var deleteBt: ImageButton = itemView.findViewById(R.id.item_delete_bt)
        var currencyTxt : TextView = itemView.findViewById(R.id.item_currency_tx)
    }

}