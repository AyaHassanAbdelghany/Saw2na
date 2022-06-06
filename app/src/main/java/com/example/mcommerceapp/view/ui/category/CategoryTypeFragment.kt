package com.example.mcommerceapp.view.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentCategoryTypeBinding

import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.view.ui.category.adapter.CategoryAdapter
import com.example.mcommerceapp.view.ui.category.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.feature_product.CategorizedProductActivity
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModel
import com.example.mcommerceapp.view.ui.home.viewmodel.HomeViewModelFactory

class CategoryTypeFragment (): OnClickListener,Fragment() {

    private var tabTitle :String = ""
    private var vendor :String = ""
    private var type :String = ""
    private var subCollection :String = ""

    private lateinit var binding: FragmentCategoryTypeBinding
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var categoryVMFactory: CategoryViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter

   constructor(tabTitle:String, vendor :String, type:String) : this() {
       this.tabTitle = tabTitle
       this.vendor = vendor
       this.type = type
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryTypeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        categoryVM.getCollectionId(tabTitle)
        when(type){
            Keys.VENDOR -> observeVendor()
            Keys.COLLECTION -> observeCollection()
        }
    }

    private fun observeCollection(){
       categoryVM.customCollection.observe(viewLifecycleOwner){
           subCollection = it[0].id.toString()
           categoryVM.getCategoryForCollection(Keys.PRODUCT_TYPE, it[0].id.toString())
           observerCategory()
       }
    }
    private fun observeVendor(){
        categoryVM.customCollection.observe(viewLifecycleOwner){

            subCollection = it[0].id.toString()
            categoryVM.getCategoryForVendor(Keys.PRODUCT_TYPE,it[0].id.toString(),vendor)
            observerCategory()
        }

    }
    private fun observerCategory(){
        categoryVM.category.observe(viewLifecycleOwner){
           binding.progressBar.visibility = ProgressBar.INVISIBLE
            categoryAdapter.setData(it)
            binding.recyclerListCategory.adapter = categoryAdapter
        }
    }
    private fun init(){
        categoryVMFactory = CategoryViewModelFactory(ProductRepo.getInstance(RemoteSource()))
        categoryVM = ViewModelProvider(this, categoryVMFactory)[CategoryViewModel::class.java]
        categoryAdapter = CategoryAdapter(this)
    }

    override fun onClick(value: String) {
        val bundle = Bundle()
        bundle.putString("PRODUCT_TYPE", value)
        bundle.putString("TYPE", type)
        bundle.putString("SUB_CATEGORY", subCollection)
        bundle.putString("VENDOR", vendor)
        val intent = Intent(requireContext(), CategorizedProductActivity::class.java)
        intent.putExtra("PRODUCTS", bundle)
        startActivity(intent)
    }

}