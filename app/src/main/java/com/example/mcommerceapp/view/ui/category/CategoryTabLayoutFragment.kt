package com.example.mcommerceapp.view.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentCategoryBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.category.adapter.PagerAdapter
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModel
import com.example.mcommerceapp.view.ui.category.viewmodel.CategoryViewModelFactory
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteScreen
import com.example.mcommerceapp.view.ui.search.view.SearchActivity
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class CategoryTabLayoutFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var categoryVM: CategoryViewModel
    private lateinit var categoryVMFactory: CategoryViewModelFactory
    private val myList = listOf(getString(R.string.all_types), getString(R.string.men_type), getString(
        R.string.women_type), getString(R.string.kids_type), getString(R.string.sale_type))
    private lateinit var myEnglishList:List<String>

    private var isLoggedIn = false


    private val pagerCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)!!.select()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        init()

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = listOf(getString(R.string.all_types), getString(R.string.men_type), getString(
                            R.string.women_type), getString(R.string.kids_type), getString(R.string.sale_type))[position]
        }.attach()

        binding.actionBar.backImg.visibility = ImageView.INVISIBLE

        binding.actionBar.favouriteImage.setOnClickListener {
            if (!isLoggedIn) {
                startActivity(Intent(requireContext(), SigninActivity::class.java))
            } else {
                startActivity(
                    Intent(
                        requireContext(),
                        FavoriteScreen::class.java
                    )
                )
            }
        }

        binding.actionBar.cardImage.setOnClickListener {
            if (!isLoggedIn) {
                startActivity(Intent(requireContext(), SigninActivity::class.java))
            } else {
                startActivity(
                    Intent(
                        requireContext(),
                        ShoppingCartScreen::class.java
                    )
                )
            }
        }

        binding.actionBar.searchImage.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    SearchActivity::class.java
                )
            )
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isLoggedIn = categoryVM.isLogged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("ResourceType")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.adapter = pagerAdapter
                pagerAdapter.tabTitle = myEnglishList[myList.indexOf(tab?.text as String)]
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        var res = resources
        val confg = res.configuration
        val savedLocal = confg.locale
        confg.locale = Locale("en")
        res.updateConfiguration(confg,null)
        myEnglishList = listOf(res.getString(R.string.all_types), res.getString(R.string.men_type), res.getString(
            R.string.women_type), res.getString(R.string.kids_type), res.getString(R.string.sale_type))
        confg.locale = savedLocal
        res.updateConfiguration(confg,null)

    }

    private fun init() {
        pagerAdapter = PagerAdapter(this.childFragmentManager, this.lifecycle)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(pagerCallback)

        categoryVMFactory = CategoryViewModelFactory(
            ProductRepo.getInstance(ProductRemoteSource.getInstance()), CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(), requireContext
                    ()
            ),
            UserRepo.getInstance(requireContext())
        )
        categoryVM = ViewModelProvider(this, categoryVMFactory)[CategoryViewModel::class.java]
    }
}