package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.MainActivity
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentCategoryBinding
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.category.adapter.PagerAdapter
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteScreen
import com.example.mcommerceapp.view.ui.search.SearchActivity
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar


class CategoryTabLayoutFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var pagerAdapter: PagerAdapter


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

        init()

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = listOf("ALL","MEN", "WOMEN", "KID","SALE")[position]
         }.attach()

        binding.actionBar.backImg.visibility = ImageView.VISIBLE

        binding.actionBar.backImg.setOnClickListener { startActivity(Intent(requireContext(), MainActivity::class.java)) }

        binding.actionBar.favouriteImage.setOnClickListener { startActivity(Intent(requireContext(), FavoriteScreen::class.java)) }

        binding.actionBar.cardImage.setOnClickListener { startActivity(Intent(requireContext(), ShoppingCartScreen::class.java)) }

        binding.actionBar.searchImage.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("ResourceType")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.adapter = pagerAdapter
                pagerAdapter.tabTitle = tab?.text as String
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

 private  fun init(){
     pagerAdapter = PagerAdapter(this.childFragmentManager, this.lifecycle)
     binding.viewPager.adapter = pagerAdapter
     binding.viewPager.isUserInputEnabled = false
     binding.viewPager.registerOnPageChangeCallback(pagerCallback)
 }
}