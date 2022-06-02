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

class ColorAdapter(private var listColor: List<String>, var context: Context): RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_color, parent, false)
        return ViewHolder(view)
    }

    fun setColorList(listSize: List<String>) {
        this.listColor = listSize
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val intColor = Color.parseColor(listColor[position])
        val hexColor = Integer.toHexString(intColor).substring(2)
        holder.colorCard.setCardBackgroundColor((Color.parseColor("#${hexColor}")))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val colorCard: CardView = view.findViewById(R.id.color_card)
    }

    override fun getItemCount(): Int = listColor.size
}
