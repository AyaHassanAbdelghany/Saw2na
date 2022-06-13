package com.example.mcommerceapp.view.ui.category.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.mcommerceapp.view.ui.category.CategoryTypeFragment

class PagerAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {


    var tabTitle :String="ALL"

    override fun createFragment(position: Int): Fragment {
        return CategoryTypeFragment(tabTitle)
    }

    override fun getItemCount(): Int {
        return  5
    }

    override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}