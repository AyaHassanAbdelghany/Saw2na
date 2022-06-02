package com.example.mcommerceapp.view.ui.category.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mcommerceapp.view.ui.category.CategoryTypeFragment

class PagerAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {

    var value :String= ""
    var type :String= ""
    var tabTitle :String="MEN"

    override fun createFragment(position: Int): Fragment {
        return CategoryTypeFragment(tabTitle,value,type)
    }

    override fun getItemCount(): Int {
        return  3
    }

}