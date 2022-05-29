package com.example.mcommerceapp.view.ui.home

import com.example.mcommerceapp.model.shopify_repository.ShopifyRepo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentHomeBinding
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.view.ui.home.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodelfactory.HomeViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        homeVM.getProduct()
        observeCourses()
    }

    private fun init(){
        homeVMFactory = HomeViewModelFactory(ShopifyRepo.getInstance(RemoteSource()))
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
        categoryAdapter = CategoryAdapter()
        binding.recyclerListCatogery.adapter = categoryAdapter
    }

    private fun observeCourses(){
        homeVM.catogeries.observe(viewLifecycleOwner){
            categoryAdapter.setData(it)
            binding.recyclerListCatogery.adapter = categoryAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}