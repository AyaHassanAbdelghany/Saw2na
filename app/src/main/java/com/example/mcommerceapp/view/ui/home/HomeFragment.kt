package com.example.mcommerceapp.view.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentHomeBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections
import com.example.mcommerceapp.view.ui.home.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.home.adapter.VendorAdapter
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodelfactory.HomeViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var vendorAdapter: VendorAdapter


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
        homeVM.getProduct(Keys.PRODUCT_TYPE)
        observerVendors()
        observerCategories()
    }

    private fun init(){
        homeVMFactory = HomeViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
        categoryAdapter = CategoryAdapter()
        vendorAdapter = VendorAdapter(requireContext())
        binding.recyclerListVendor.adapter = vendorAdapter
        binding.recyclerListCatogery.adapter = categoryAdapter
    }

    private fun observerVendors(){
        homeVM.vendors.observe(viewLifecycleOwner){
            vendorAdapter.setData(it)
            binding.recyclerListVendor.adapter = vendorAdapter
        }
    }

    private fun observerCategories(){
        homeVM.catogeries.observe(viewLifecycleOwner){
            categoryAdapter.setData(it)
            binding.recyclerListCatogery.adapter = categoryAdapter
        }
    }
}