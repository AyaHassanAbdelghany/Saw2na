package com.example.mcommerceapp.view.ui.product_detail.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R

class SizeAdapter(private var listSize: List<String>, var context: Context): RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_size, parent, false)
        return ViewHolder(view)
    }

    fun setSizeList(listSize: List<String>) {
        this.listSize = listSize
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.sizeTxt.text = listSize!![position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val sizeTxt: TextView = view.findViewById(R.id.sizeTxt)
    }

    override fun getItemCount(): Int = listSize.size
}
