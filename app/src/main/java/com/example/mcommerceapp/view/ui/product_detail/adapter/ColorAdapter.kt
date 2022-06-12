package com.example.mcommerceapp.view.ui.product_detail.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.R
import com.example.mcommerceapp.pojo.products.Variants

class ColorAdapter( var context: Context, var listener: OnClickListener): RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private lateinit var listColor: HashSet<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_color, parent, false)
        return ViewHolder(view)
    }

    fun setColorList(listSize:  HashSet<String>) {
        this.listColor = listSize
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val color = listColor.elementAt(position)
        val intColor = Color.parseColor(color)
        val hexColor = Integer.toHexString(intColor).substring(2)
        holder.colorCard.setCardBackgroundColor((Color.parseColor("#${hexColor}")))

        holder.itemView.setOnClickListener {
            listener.onClickColor(color)
            holder.itemView.setBackgroundResource(R.drawable.colored_border_button_background)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val colorCard: CardView = view.findViewById(R.id.color_card)
    }

    override fun getItemCount(): Int = listColor.size
}
