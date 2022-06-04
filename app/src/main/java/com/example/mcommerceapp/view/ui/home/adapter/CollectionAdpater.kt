package com.example.mcommerceapp.view.ui.home.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceapp.databinding.ItemListCollectionBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.pojo.products.ProductFields

class CollectionAdpater  (private var listner : OnClickListner): RecyclerView.Adapter<CollectionAdpater.ViewHolder>(){

    private  var collection : HashSet<ProductFields> = hashSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListCollectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = collection.elementAt(position)
        holder.binding.apply {
           collectionNameText.text = currentItem.productType
        }
        holder.binding.constraintCollection.setOnClickListener(
            View.OnClickListener
        { listner!!.onClick(currentItem.productType,Keys.COLLECTION) })
    }

    override fun getItemCount(): Int {
        return collection.count()
    }

    fun setData(collection: HashSet<ProductFields>){
        this.collection = collection
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListCollectionBinding): RecyclerView.ViewHolder(binding.root)
}