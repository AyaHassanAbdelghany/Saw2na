package com.example.mcommerceapp.view.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentHomeBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.home.adapter.CollectionAdpater
import com.example.mcommerceapp.view.ui.home.adapter.OnClickListner
import com.example.mcommerceapp.view.ui.home.adapter.VendorAdapter
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory


class HomeFragment() : OnClickListner,Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeViewModel
    private lateinit var homeVMFactory: HomeViewModelFactory
    private lateinit var collectionAdapter: CollectionAdpater
    private lateinit var vendorAdapter: VendorAdapter


     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()

        binding.advertise.setOnClickListener {
            findNavController(this)?.navigate(R.id.actCategory)
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        homeVM.getProduct(Keys.PRODUCT_TYPE)
        observerVendors()
        observerCollections()
    }

    private fun init(){
        homeVMFactory = HomeViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        homeVM = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
        collectionAdapter = CollectionAdpater(this)
        vendorAdapter = VendorAdapter(requireContext(),this)
        binding.recyclerListVendor.adapter = vendorAdapter
        binding.recyclerListCollection.adapter = collectionAdapter
    }

    private fun observerVendors(){
        homeVM.vendors.observe(viewLifecycleOwner){
            vendorAdapter.setData(it)
            binding.recyclerListVendor.adapter = vendorAdapter
        }
    }

    private fun observerCollections(){
        homeVM.collections.observe(viewLifecycleOwner){
            collectionAdapter.setData(it)
            binding.recyclerListCollection.adapter = collectionAdapter

        }
    }

    override fun onClick(value: String?,type:String) {
        val bundle = Bundle()
        bundle.putString("VALUE", value)
        bundle.putString("TYPE", type)
        findNavController(this)?.navigate(R.id.actCategory,bundle);

    }
    fun findNavController(fragment: Fragment): NavController? {
        val view = fragment.view
        return findNavController(view!!)
    }
}