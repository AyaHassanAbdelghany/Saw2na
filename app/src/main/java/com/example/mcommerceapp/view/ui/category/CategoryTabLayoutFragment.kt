package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.databinding.FragmentCategoryBinding
import com.example.mcommerceapp.view.ui.category.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CategoryTabLayoutFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var pagerAdapter: PagerAdapter
    private  var value :String =""

        private val pagerCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)!!.select()
            }
        } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        pagerAdapter = PagerAdapter(this.childFragmentManager, this.lifecycle)
        binding.viewPager.adapter = pagerAdapter
       binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(pagerCallback)
//
//        var bun = this.arguments
//
//        if (bun != null) {
//            value = bun.getString("VALUE")!!
//            type = bun.getString("TYPE")!!
//        }
//
//        pagerAdapter.vendor = value
//        pagerAdapter.type = type

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = listOf<String>("MEN", "WOMEN", "KID","ALL")[position]
         }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("ResourceType")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.adapter = pagerAdapter
                pagerAdapter.tabTitle = tab?.text as String
                binding.viewPager.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }

}