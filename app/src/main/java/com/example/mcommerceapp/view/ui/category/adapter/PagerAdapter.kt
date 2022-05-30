package com.example.mcommerceapp.view.ui.category.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mcommerceapp.view.ui.category.CategoryTypeFragment

class PagerAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        return CategoryTypeFragment()
    }

    override fun getItemCount(): Int {
        return  3
    }

}