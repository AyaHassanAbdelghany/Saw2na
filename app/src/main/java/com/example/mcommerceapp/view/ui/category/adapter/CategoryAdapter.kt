package com.example.mcommerceapp.view.ui.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mcommerceapp.R

class CategoryAdapter (private val context: Context): BaseAdapter() {

    private  var category : MutableSet<String> = mutableSetOf()
    private val listner: OnCategoryClickListner? = null

    private var layoutInflater: LayoutInflater? = null
    private lateinit var categoryImage: ImageView
    private lateinit var categoryName: TextView

    override fun getCount(): Int {
        return category.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.item_list_catogery, null)
        }

        categoryName = convertView!!.findViewById(R.id.catogeryName_text)
        categoryImage = convertView.findViewById(R.id.catogery_image)
        categoryName.text = category.elementAt(position)
//        Glide.with(convertView)
//            .load(categoryImage)
//            .into(categoryImage)
        return convertView
    }

    fun setData(category: MutableSet<String>){
        this.category = category
        notifyDataSetChanged()
    }
}