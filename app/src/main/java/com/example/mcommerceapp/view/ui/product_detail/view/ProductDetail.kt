package com.example.mcommerceapp.view.ui.product_detail.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityProductDetailBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import com.example.mcommerceapp.view.ui.product_detail.ImageSlideAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.ColorAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.SizeAdapter
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM
import com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory.ProductDetailVMFactory


class ProductDetail : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailBinding
    private lateinit var imageSliderPager: ImageSlideAdapter
    private lateinit var sizeAdapter: SizeAdapter
    lateinit var colorAdapter: ColorAdapter
    private lateinit var detailVM: ProductDetailVM
    private lateinit var detailVMFactory: ProductDetailVMFactory

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        detailVMFactory = ProductDetailVMFactory(
            ProductRepo.getInstance(RemoteSource()), RoomRepo.getInstance(
                LocalSource(this), this
            ), CurrencyRepo.getInstance(RemoteSource(), this)
        )
        detailVM = ViewModelProvider(this, detailVMFactory)[ProductDetailVM::class.java]

        val intent = intent.getStringExtra("PRODUCTS_ID")

        Log.e("Product Details : ", intent.toString())
        detailVM.checkForFavouriteProductById(intent!!)
        detailVM.isFav.observe(this) {
            if (it == 1) {
                binding.detailBtn.favImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_baseline_favorite_24 // Drawable
                    )
                )
            } else {
                binding.detailBtn.favImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_baseline_favorite_border_24 // Drawable
                    )
                )
            }
        }

        detailVM.getProductDetail(intent!!)
        detailVM.productDetail.observe(this) {
            binding.detailBtn.favImage.setOnClickListener { view ->
                if (detailVM.isFav.value == 1) {
                    binding.detailBtn.favImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext, // Context
                            R.drawable.ic_baseline_favorite_border_24 // Drawable
                        )
                    )
                    detailVM.deleteFavoriteProduct(
                        FavProducts(
                            productPrice = it.variants[0].price?.toDouble()!!,
                            productId = it.id.toString()!!,
                            productImage = "",
                            productName = it.title!!
                        )
                    )
                } else {
                    binding.detailBtn.favImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext, // Context
                            R.drawable.ic_baseline_favorite_24 // Drawable
                        )
                    )

                    detailVM.insertFavoriteProduct(
                        FavProducts(
                            productPrice = it.variants[0].price?.toDouble()!!,
                            productId = it.id!!,
                            productImage = it.image?.src!!,
                            productName = it.title!!
                        )
                    )
                }
            }

            binding.contentDetail.ProductPriceTxt.text = "${
                it.variants[0].price?.toDouble()?.times(detailVM.currencyValue)
            } ${detailVM.currencySymbol}"
            binding.contentDetail.ProductRating.rating =
                (it.variants[0].inventoryQuantity)!!.toFloat()
            imageSliderPager = ImageSlideAdapter(this, it.images)
            binding.viewPagerMain.adapter = imageSliderPager
            binding.indicator.setViewPager(binding.viewPagerMain)

            binding.contentDetail.sizeRecycleView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            sizeAdapter = SizeAdapter(it.options[0].values, this)
            binding.contentDetail.sizeRecycleView.adapter = sizeAdapter

            binding.contentDetail.colorRecycleView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            colorAdapter = ColorAdapter(it.options[1].values, this)
            binding.contentDetail.colorRecycleView.adapter = colorAdapter

            binding.contentDetail.readMore.text = it.bodyHtml

            binding.contentDetail.card1.reviewerNameTxt.text = Keys.REVIEWS[0].name
            binding.contentDetail.card1.reviewerDateTxt.text = Keys.REVIEWS[0].date
            binding.contentDetail.card1.reviewerRaring.rating = Keys.REVIEWS[0].rate
            binding.contentDetail.card1.reviewerDescTxt.text = Keys.REVIEWS[0].desc

            binding.contentDetail.card2.reviewerNameTxt.text = Keys.REVIEWS[1].name
            binding.contentDetail.card2.reviewerDateTxt.text = Keys.REVIEWS[1].date
            binding.contentDetail.card2.reviewerRaring.rating = Keys.REVIEWS[1].rate
            binding.contentDetail.card2.reviewerDescTxt.text = Keys.REVIEWS[1].desc
        }
    }
}