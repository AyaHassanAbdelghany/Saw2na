package com.example.mcommerceapp.view.ui.category.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ItemListCollectionBinding
import com.example.mcommerceapp.databinding.ProductCardBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.view.ui.home.adapter.CollectionAdpater
import com.example.mcommerceapp.view.ui.home.adapter.OnClickListner

class CategoryAdapter  (private var context :Context ,private var listner : OnClickListener): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private  var category : HashSet<ProductFields> = hashSetOf()
    private  var collectionProducts : ArrayList<Products> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = collectionProducts[position]
        holder.binding.apply {
            productNameTxt.text = currentItem.title

            Glide.with(context)
                .load(currentItem.image?.src)
                .into(productImage)
            productPriceTxt.text = currentItem.variants.get(0).price.toString()
        }
//        when(currentItem.productType ){
//            "T-SHIRTS"->{
//                holder.binding.collectionImage.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.t_shirt
//                    )
//                )
//            }
//            "SHOES"  ->{
//                holder.binding.collectionImage.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context,
//                        R.drawable.shoes
//                    )
//                )
//            }
//            else ->{
//                holder.binding.collectionImage.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        context, // Context
//                        R.drawable.assesores // Drawable
//                    )
//                )
//            }
//        }
//        holder.binding.constraintCollection.setOnClickListener(
//            View.OnClickListener
//            { listner!!.onClick(currentItem.productType) })
        holder.itemView.setOnClickListener {
           listner.onClick(currentItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return collectionProducts.count()
    }

    fun setData(collectionProducts: ArrayList<Products>){
        this.collectionProducts = collectionProducts
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ProductCardBinding): RecyclerView.ViewHolder(binding.root)
}