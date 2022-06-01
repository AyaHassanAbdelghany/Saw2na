package com.example.mcommerceapp.view.ui.feature_product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.R
import com.example.mcommerceapp.pojo.products.Products

class CategorizedProductAdapter(private val context: Context,
                                private val productList: List<String>,
) :
BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var productImage: ImageView
    private lateinit var productPrice: TextView
    private lateinit var productName: TextView

    override fun getCount(): Int {
        return productList.size
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
            convertView = layoutInflater!!.inflate(R.layout.product_card, null)
        }
        productImage = convertView!!.findViewById(R.id.productImage)
        productPrice = convertView.findViewById(R.id.productNameTxt)
        productPrice = convertView.findViewById(R.id.ProductPriceTxt)

        Glide.with(convertView)
            .load("https://cdn.shopify.com/s/files/1/0589/7509/2875/products/8072c8b5718306d4be25aac21836ce16.jpg?v=1653403070")
            .into(productImage)

        //productList[position].image
        //productList[position].title
        //productList[position].variants[position].price
      //  productName.text = "bala7"
        productPrice.text = "hyjn$"
        return convertView
    }
}