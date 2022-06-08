package com.example.mcommerceapp.view.ui.home


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentHomeBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.feature_product.adapter.AllProductsAdapter
import com.example.mcommerceapp.view.ui.home.adapter.AdvAdapter
import com.example.mcommerceapp.view.ui.home.adapter.CollectionAdpater
import com.example.mcommerceapp.view.ui.home.adapter.OnClickListner
import com.example.mcommerceapp.view.ui.home.adapter.VendorAdapter
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory
import com.example.mcommerceapp.view.ui.product_detail.view.ProductDetail


class HomeFragment : OnClickListner, Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory
    private lateinit var collectionAdapter: CollectionAdpater
    private lateinit var vendorAdapter: VendorAdapter
    private lateinit var allProductsAdapter: AllProductsAdapter
    private lateinit var sliderItemList: ArrayList<Int>
    private lateinit var advAdapter: AdvAdapter
    private lateinit var sliderHandler: Handler
    private lateinit var sliderRun: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        homeVM.getProduct()
        observerVendors()
        //observerCollections()
        observerAllProducts()
        sliderItems()
        itemSliderView()
    }

    private fun itemSliderView() {
        sliderItemList.add(R.drawable.adv_bags)
        sliderItemList.add(R.drawable.adv_bag_2)
        sliderItemList.add(R.drawable.adv_bag_3)
        sliderItemList.add(R.drawable.adv_dress)
    }

    private fun sliderItems() {
        sliderItemList = ArrayList()
        advAdapter = AdvAdapter(binding.advViewPager, sliderItemList)
        binding.advViewPager.adapter = advAdapter
        binding.advViewPager.clipToPadding = false
        binding.advViewPager.clipChildren = false
        binding.advViewPager.offscreenPageLimit = 1
        binding.advViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val comPosPageTarn = CompositePageTransformer()
        comPosPageTarn.addTransformer(MarginPageTransformer(40))
        comPosPageTarn.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.advViewPager.setPageTransformer(comPosPageTarn)
        sliderHandler = Handler()
        sliderRun = Runnable {
            binding.advViewPager.currentItem = binding.advViewPager.currentItem + 1
        }
        binding.advViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    sliderHandler.removeCallbacks(sliderRun)
                    sliderHandler.postDelayed(sliderRun, 3000)
                }
            })

    }

    private fun init() {
        homeVMFactory = HomeViewModelFactory(
            ProductRepo.getInstance(RemoteSource()),
            ProductRepo.getInstance(RemoteSource())
        )
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
        collectionAdapter = CollectionAdpater(this, requireContext())
        vendorAdapter = VendorAdapter(requireContext(), this)
        allProductsAdapter = AllProductsAdapter(requireContext(), this)
        binding.recyclerListVendor.adapter = vendorAdapter
        //binding.recyclerListCollection.adapter = collectionAdapter
        binding.recycleViewProduct.adapter = allProductsAdapter

    }

    private fun observerVendors() {
        homeVM.vendors.observe(viewLifecycleOwner) {
            vendorAdapter.setData(it)
            binding.recyclerListVendor.adapter = vendorAdapter
        }
    }

    private fun observerAllProducts() {
        homeVM.allProducts.observe(viewLifecycleOwner) {
            allProductsAdapter.setData(it)
            binding.recycleViewProduct.adapter = allProductsAdapter

        }
    }

    override fun onClick(value: String?, type: String) {
        val bundle = Bundle()
        Log.e("type", type)
        when (type) {

            Keys.VENDOR -> {
                bundle.putString("VALUE", value)
                bundle.putString("TYPE", type)
                val intent = Intent(requireContext(), CategorizedProductActivity::class.java)
                intent.putExtra("PRODUCTS", bundle)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(requireContext(), ProductDetail::class.java)
                intent.putExtra("PRODUCTS_ID", value)
                startActivity(intent)
            }
        }

    }

}