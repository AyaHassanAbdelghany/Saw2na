package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.databinding.FragmentCategoryBinding
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.category.adapter.PagerAdapter
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CollectionTabLayoutFragment : Fragment() {

    private  var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var pagerAdapter: PagerAdapter
    private  var value :String =""
    private  var type :String = ""

    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory

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
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        pagerAdapter = PagerAdapter(this.childFragmentManager, this.lifecycle)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.registerOnPageChangeCallback(pagerCallback)

        var bun = this.arguments

        if (bun != null) {
            value = bun.getString("VALUE")!!
            type = bun.getString("TYPE")!!
        }

        pagerAdapter.value = value
        pagerAdapter.type = type

        homeVMFactory = HomeViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->

            tab.text = listOf<String>("MEN", "WOMEN", "KIDS")[position]
         }.attach()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("ResourceType")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pagerAdapter.tabTitle = tab?.text as String
                binding.viewPager.currentItem = tab.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}