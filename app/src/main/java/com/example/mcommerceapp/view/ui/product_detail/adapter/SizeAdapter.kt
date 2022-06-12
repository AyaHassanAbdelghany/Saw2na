package com.example.mcommerceapp.view.ui.product_detail.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.pojo.products.Variants

class SizeAdapter(var context: Context, var listener: OnClickListener): RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private lateinit var listSize: ArrayList<Variants>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_size, parent, false)
        return ViewHolder(view)
    }

    fun setSizeList(listSize: ArrayList<Variants>) {
        this.listSize = listSize
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val size = listSize!![position].option1
        holder.sizeTxt.text = size
        holder.itemView.setOnClickListener {
            listener.onClickSize(size!!)
            holder.itemView.setBackgroundResource(R.drawable.colored_border_button_background)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val sizeTxt: TextView = view.findViewById(R.id.sizeTxt)
    }

    override fun getItemCount(): Int = listSize.size
}
