package com.example.mcommerceapp.view.ui.category.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.mcommerceapp.view.ui.category.CategoryTypeFragment

class PagerAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {

    var vendor :String= ""
    var type :String= ""
    var tabTitle :String="MEN"

    override fun createFragment(position: Int): Fragment {
        return CategoryTypeFragment(tabTitle,vendor,type)
    }

    override fun getItemCount(): Int {
        return  4
    }

    override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
        Log.e("pager","hello")
        super.onViewDetachedFromWindow(holder)
    }
}