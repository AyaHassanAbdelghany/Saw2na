package com.example.mcommerceapp.view.ui.product_detail.view

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.local_source.LocalSource
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.room_repository.RoomRepo
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.favorite_products.FavProducts
import com.example.mcommerceapp.pojo.products.Variants
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.product_detail.ImageSlideAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.ColorAdapter
import com.example.mcommerceapp.view.ui.product_detail.adapter.OnClickListener
import com.example.mcommerceapp.view.ui.product_detail.adapter.SizeAdapter
import com.example.mcommerceapp.view.ui.product_detail.viewmodel.ProductDetailVM
import com.example.mcommerceapp.view.ui.product_detail.viewmodelfactory.ProductDetailVMFactory
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.material.snackbar.Snackbar
import draft_orders.DraftOrder
import draft_orders.LineItems
import draft_orders.NoteAttributes


class ProductDetail : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityProductDetailBinding
    private lateinit var imageSliderPager: ImageSlideAdapter
    private lateinit var sizeAdapter: SizeAdapter
    lateinit var colorAdapter: ColorAdapter
    private lateinit var detailVM: ProductDetailVM
    private lateinit var detailVMFactory: ProductDetailVMFactory

    private lateinit var color: String
    private lateinit var size: String
    private lateinit var image: String
    private var id: Long = -1
    private var isFav = -1
    private lateinit var variant: ArrayList<Variants>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        detailVMFactory = ProductDetailVMFactory(
            ProductRepo.getInstance(RemoteSource()), RoomRepo.getInstance(
                LocalSource(this), this
            ), CurrencyRepo.getInstance(RemoteSource(), this),
            DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this)
        )
        detailVM = ViewModelProvider(this, detailVMFactory)[ProductDetailVM::class.java]

        val intent = intent.getStringExtra("PRODUCTS_ID")

        detailVM.getDraftOrder()


        Log.e("Product Details : ", intent.toString())
        detailVM.checkForFavouriteProductById(intent!!)
//        detailVM.isFav.observe(this) {
//            if (it == 1) {
//                binding.detailBtn.favImage.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        applicationContext, // Context
//                        R.drawable.ic_baseline_favorite_24_black // Drawable
//                    )
//                )
//            } else {
//                binding.detailBtn.favImage.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        applicationContext, // Context
//                        R.drawable.ic_baseline_favorite_border_24 // Drawable
//                    )
//                )
//            }
//        }

        detailVM.getProductDetail(intent)
        detailVM.productDetail.observe(this) { products ->

            variant = products.variants
            color = variant[0].option2!!
            size = variant[0].option1!!
            image = products.image?.src!!

            detailVM.favList.observe(this) { favList ->
                if (favList.count() != 0) {
                    favList.forEach { id ->
                        if (products.id == id) {
                            isFav = 1
                            Log.d("favvvvvvvvvv", isFav.toString())
                            binding.detailBtn.favImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext, // Context
                                    R.drawable.ic_baseline_favorite_24_black
                                )
                            )
                        } else {
                            isFav = 0
                            Log.d("favvvvvvvvvv", isFav.toString())
                            binding.detailBtn.favImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext, // Context
                                    R.drawable.ic_baseline_favorite_border_24
                                )
                            )
                        }
                    }
                } else {
                    isFav = 0
                    Log.d("favvvvvvvvvv", isFav.toString())
                    binding.detailBtn.favImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext, // Context
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    )

                }
            }
            binding.detailBtn.favImage.setOnClickListener {
                if (isFav == 0) {
                    id = getVariant(products.variants, color, size)
                    detailVM.addOrder(
                        DraftOrder(
                            note = Keys.FAV, email = detailVM.user.email,
                            noteAttributes = arrayListOf(
                                NoteAttributes(
                                    name = "image",
                                    value = image
                                )
                            ),
                            lineItems = arrayListOf<LineItems>(
                                LineItems(
                                    variantId = id,
                                    productId = products.id,
                                    quantity = 1,
                                    title = products.title,
                                    price = products.variants[0].price
                                )
                            )
                        )
                    )
                    binding.detailBtn.favImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext, // Context
                            R.drawable.ic_baseline_favorite_24_black
                        )
                    )
                } else {
                    detailVM.deleteOrder(products.id!!)
                    binding.detailBtn.favImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext, // Context
                            R.drawable.ic_baseline_favorite_border_24
                        )
                    )
                }


//                if (detailVM.isFav.value == 1) {
//                    binding.detailBtn.favImage.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            applicationContext, // Context
//                            R.drawable.ic_baseline_favorite_border_24 // Drawable
//                        )
//                    )
////                    detailVM.deleteFavoriteProduct(
////                        FavProducts(
////                            productPrice = products.variants[0].price?.toDouble()!!,
////                            productId = products.id.toString()!!,
////                            productImage = "",
////                            productName = products.title!!
////                        )
////                    )
//                   // detailVM.deleteOrder()
//                }
//                else {
//                    binding.detailBtn.favImage.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            applicationContext, // Context
//                            R.drawable.ic_baseline_favorite_24_black // Drawable
//                        )
//                    )

//                    detailVM.insertFavoriteProduct(
//                        FavProducts(
//                            productPrice = products.variants[0].price?.toDouble()!!,
//                            productId = products.id!!,
//                            productImage = products.image?.src!!,
//                            productName = products.title!!
//                        )
//                    )


            }

            binding.detailBtn.checkoutBtn.setOnClickListener {

                if (!detailVM.isLogged) {
                    startActivity(Intent(this, SigninActivity::class.java))
                } else {
                    Log.d("add to cart", "99999999999999")
                    id = getVariant(products.variants, color, size)
                    detailVM.cartList.observe(this) { cart ->
                        Log.d("add to cart", cart.toString())
                        if (cart.count() == 0) {
                            detailVM.addOrder(
                                DraftOrder(
                                    note = Keys.CART, email = detailVM.user.email,
                                    noteAttributes = arrayListOf(NoteAttributes(value = image)),
                                    lineItems = arrayListOf<LineItems>(
                                        LineItems(variantId = id, quantity = 1)
                                    )
                                )
                            )
                            Log.d("add to cart", "add")
                            Snackbar.make(
                                binding.layout,
                                "Added to cart...",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            id = getVariant(variant, color, size)
                            cart.forEach { cartId ->
                                if (cartId != id) {
                                    detailVM.addOrder(
                                        DraftOrder(
                                            note = Keys.CART, email = detailVM.user.email,
                                            noteAttributes = arrayListOf(NoteAttributes(value = image)),
                                            lineItems = arrayListOf<LineItems>(
                                                LineItems(variantId = id, quantity = 1)
                                            )
                                        )
                                    )
                                    Log.d("add to cart", "add")
                                    Snackbar.make(
                                        binding.layout,
                                        "Added to cart...",
                                        Snackbar.LENGTH_LONG
                                    ).show()

                                } else {
                                    Snackbar.make(
                                        binding.layout,
                                        "Already Added.....",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    Log.d("add to cart", "add")
                                }
                            }
                        }
                    }
                }
            }
            binding.toolbar.title = products.title

            binding.contentDetail.ProductPriceTxt.text = "${
                products.variants[0].price?.toDouble()?.times(detailVM.currencyValue)
            } ${detailVM.currencySymbol}"
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
                set.add(color.option2!!)
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
}