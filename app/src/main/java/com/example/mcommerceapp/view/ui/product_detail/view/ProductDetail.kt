package com.example.mcommerceapp.view.ui.product_detail.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityProductDetailBinding
import com.example.mcommerceapp.model.Keys
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.shopify_repository.draft_orders.DraftOrdersRepo
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.products.Variants
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.product_detail.ImageSlideAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.ColorAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.product_detail.adapter.SizeAdapter
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM
import com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory.ProductDetailVMFactory
import com.example.mcommerceapp.view.ui.review.ReviewActivity
import com.google.android.material.snackbar.Snackbar
import draft_orders.DraftOrder
import draft_orders.LineItems
import draft_orders.NoteAttributes


class ProductDetail : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityProductDetailBinding
    private lateinit var imageSliderPager: ImageSlideAdapter
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var detailVM: ProductDetailVM
    private lateinit var detailVMFactory: ProductDetailVMFactory

    private lateinit var color: String
    private lateinit var size: String
    private lateinit var image: String
    private lateinit var productDetail: Products
    private var id: Long = -1
    private var isFav = 0
    private var isCart = 0
    private lateinit var variant: ArrayList<Variants>

    private var isLoggedIn = false

    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = Dialog(this)

        detailVMFactory = ProductDetailVMFactory(
            ProductRepo.getInstance(
                ProductRemoteSource.getInstance()
            ),
            RoomRepo.getInstance(
                LocalSource(this),
                this
            ),
            CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(),
                this
            ),
            DraftOrdersRepo.getInstance(
                DraftOrdersRemoteSource.getInstance()
            ),
            UserRepo.getInstance(
                this
            )
        )
        detailVM = ViewModelProvider(this, detailVMFactory)[ProductDetailVM::class.java]

        val intent = intent.getStringExtra("PRODUCTS_ID")
        detailVM.getProductDetail(intent!!)
        showLoadingDialog("Loading Data")

        detailVM.cartList.observe(this) { cart ->
            loadingDialog.dismiss()
            if (cart.isNotEmpty()) {
                id = getVariant(variant, color, size)
                cart.forEach { cartId ->
                    if (cartId == id) {
                        isCart = 1
                    }
                }
            }
        }

        detailVM.favList.observe(this) { favList ->
            loadingDialog.dismiss()
            isFav = 0
            for (favId in favList) {
                Log.e("TAG", "onCreate: ${favId} -- $id")
                if (favId == productDetail.id) {
                    isFav = 1
                    break
                } else {
                    isFav = 0
                }
            }

            if (isFav == 1) {
                binding.detailBtn.favImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_baseline_favorite_24_black
                    )
                )
            } else if (isFav == 0) {
                binding.detailBtn.favImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )
            }
        }

        binding.detailBtn.checkoutBtn.setOnClickListener {
            if (!isLoggedIn) {
                startActivity(Intent(this, SigninActivity::class.java))
            } else {
                if (isCart == 0) {
                    showLoadingDialog("Adding to cart")
                    detailVM.addOrder(
                        DraftOrder(
                            note = Keys.CART, email = detailVM.user.email,
                            noteAttributes = arrayListOf(NoteAttributes(value = image)),
                            lineItems = arrayListOf(
                                LineItems(variantId = id, quantity = 1)
                            )
                        )
                    )
                    Snackbar.make(binding.layout, "Added to cart...", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(binding.layout, "Already added...", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.contentDetail.reviewSeeMoreTxt.setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java))
        }
        binding.detailBtn.favImage.setOnClickListener {
            if (!isLoggedIn) {
                startActivity(Intent(this, SigninActivity::class.java))
            } else {
                if (isFav == 0) {
                    showLoadingDialog("Adding to favorite")
                    detailVM.addOrder(
                        DraftOrder(
                            note = Keys.FAV, email = detailVM.user.email,
                            noteAttributes = arrayListOf(
                                NoteAttributes(
                                    name = "image",
                                    value = image
                                )
                            ),
                            lineItems = arrayListOf(
                                LineItems(
                                    variantId = id,
                                    productId = productDetail.id,
                                    quantity = 1,
                                    title = productDetail.title,
                                    price = productDetail.variants[0].price
                                )
                            )
                        )
                    )

                } else {
                    showLoadingDialog("Removing from favorite")
                    detailVM.deleteOrder(productDetail.id!!)
                }
            }
        }

        detailVM.productDetail.observe(this) { products ->
            if (products != null) {
                updateUI(products)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        isLoggedIn = detailVM.isLogged()
    }

    private fun updateUI(products: Products) {
        variant = products.variants
        color = variant[0].option2 ?: ""
        size = variant[0].option1 ?: ""
        image = products.image?.src ?: ""
        id = getVariant(variant, color, size)
        productDetail = products

        binding.toolbarLayout.title = products.title?.split("|")?.get(1)

        binding.contentDetail.ProductPriceTxt.text = "${products.variants[0].price?.toDouble()?.times(detailVM.currencyValue)}"
        binding.contentDetail.ProductPriceCurrencyTxt.text = detailVM.currencySymbol
        binding.contentDetail.ProductRating.rating =
            (products.variants[0].inventoryQuantity)!!.toFloat()
        imageSliderPager = ImageSlideAdapter(this, products.images)
        binding.viewPagerMain.adapter = imageSliderPager
        binding.indicator.setViewPager(binding.viewPagerMain)

        sizeAdapter = SizeAdapter(this, this)
        binding.contentDetail.sizeRecycleView.adapter = sizeAdapter
        sizeAdapter.setSizeList(products.variants)
        binding.contentDetail.sizeRecycleView.adapter = sizeAdapter

        colorAdapter = ColorAdapter(this, this)
        binding.contentDetail.colorRecycleView.adapter = colorAdapter

        val set = hashSetOf<String>()
        products.variants.forEach { color ->
            set.add(color.option2 ?: "")
        }
        colorAdapter.setColorList(set)

        binding.contentDetail.readMore.text = products.bodyHtml

        binding.contentDetail.card1.reviewerNameTxt.text = Keys.REVIEWS[0].name
        binding.contentDetail.card1.reviewerDateTxt.text = Keys.REVIEWS[0].date
        binding.contentDetail.card1.reviewerRaring.rating = Keys.REVIEWS[0].rate
        binding.contentDetail.card1.reviewerDescTxt.text = Keys.REVIEWS[0].desc

        binding.contentDetail.card2.reviewerNameTxt.text = Keys.REVIEWS[1].name
        binding.contentDetail.card2.reviewerDateTxt.text = Keys.REVIEWS[1].date
        binding.contentDetail.card2.reviewerRaring.rating = Keys.REVIEWS[1].rate
        binding.contentDetail.card2.reviewerDescTxt.text = Keys.REVIEWS[1].desc
    }

    override fun onClickColor(color: String) {
        this.color = color
    }

    override fun onClickSize(size: String) {
        this.size = size
    }

    private fun getVariant(variant: ArrayList<Variants>, color: String, size: String): Long {
        variant.forEach {
            if (it.option1 == size && it.option2 == color) {
                return it.id!!
            }
        }
        this.color = color
        this.size = size
        return variant[0].id!!
    }

    private fun showLoadingDialog(string: String) {
//        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCancelable(false)

        loadingDialog.setContentView(R.layout.dialog_wait_to_finish)
        loadingDialog.findViewById<TextView>(R.id.loading_tx).text = string

        loadingDialog.show()
    }
}