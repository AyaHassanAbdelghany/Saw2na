package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceadminapp.model.shopify_repository.coupon.CouponRepo
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityShoppingCartScreenBinding
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.remote_source.coupon.CouponRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.payment.view.Payment
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewModel
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import draft_orders.DraftOrder

class ShoppingCartScreen : AppCompatActivity(), CartCommunicator, OnClickListener {

    private lateinit var binding: ActivityShoppingCartScreenBinding

    private lateinit var discountLimit: String
    private lateinit var cartItemsAdapter: CartItemsAdapter
    private lateinit var couponsAdapter: CouponsAdapter

    private lateinit var cartViewModel: ShoppingCartViewModel
    private lateinit var cartViewModelFactory: ShoppingCartViewModelFactory

    private var cartList: ArrayList<DraftOrder> = ArrayList()
    private var codeList: ArrayList<DiscountCodes> = ArrayList()
    private var isCode = false

    private lateinit var dialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartItemsRecyclerView.setHasFixedSize(true)
        supportActionBar?.hide()

        binding.progressIndicator.visibility = View.VISIBLE

        binding.cartItemsRecyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.cartItemsRecyclerView.layoutManager = linearLayoutManager

        cartItemsAdapter = CartItemsAdapter(arrayListOf(), this, this)
        binding.cartItemsRecyclerView.adapter = cartItemsAdapter
        cartItemsAdapter = CartItemsAdapter(cartList, this, this)
        binding.cartItemsRecyclerView.adapter = cartItemsAdapter

        binding.checkoutBt.setOnClickListener {
            startActivity(Intent(this, Payment::class.java))
        }

        binding.actionBar.backImg.setOnClickListener {
            finish()
        }

        cartViewModelFactory = ShoppingCartViewModelFactory(
            DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            CouponRepo.getInstance(CouponRemoteSource())
        )

        cartViewModel =
            ViewModelProvider(this, cartViewModelFactory)[ShoppingCartViewModel::class.java]

        cartViewModel.getUser().observe(this) {
            if (it != null) {
                cartViewModel.getAllDraftOrders(it.userID)
            } else {
                print("No User Found")
            }
        }

        cartViewModel.draftOrderLiveData.observe(this) {
            if (it != null) {
                cartList = it
                binding.progressIndicator.visibility = View.INVISIBLE
                cartItemsAdapter.setOrders(cartList)
                calculateSubTotal()
            }
        }

        cartViewModel.updateLiveData.observe(this) {
            if (it != null) {
                finish()
            }
        }

        cartViewModel.getAllDiscount()
        binding.getCouponsTxt.setOnClickListener {
            showSupportBottomSheet()
        }

        binding.validationBtn.setOnClickListener {
            binding.couponEditText.isClickable = false

            for(code in codeList){
                if(binding.couponEditText.text.toString() == code.code){
                    Log.d("codeeee", "True")
                    isCode = true
                    binding.discountValueTx.text = discountLimit.drop(1)
                    break
                }else{
                    Log.d("codeeee", "False")
                    binding.couponEditText.error = "Code Wrong"
                }
            }

            if(!isCode) {
                binding.couponEditText.error = "Code Wrong"
            }
        }
    }

    override fun onBackPressed() {
        cartViewModel.updateDraftOrder(cartList)
    }

    override fun calculateNewSubTotal() {
        calculateSubTotal()
    }

    override fun deleteProductFromCart(index: Int) {
        val obj = cartList[index]
        cartViewModel.deleteProductFromDraftOrder(obj.id!!)
        deleteDraftOrderFromList(obj)
    }

    override fun increaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity++
        cartList[index].lineItems[0].quantity = (newQuantity)
    }

    override fun decreaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity--
        cartList[index].lineItems[0].quantity = (newQuantity)
    }

    private fun deleteDraftOrderFromList(draftOrder: DraftOrder) {
        cartList.remove(draftOrder)
        cartItemsAdapter.setOrders(cartList)
    }

    private fun calculateSubTotal(){
        var subTotal = 0
        cartList.forEach{ cartItem ->
           // subTotal+= (cartItem.lineItems[0].quantity!!.times(cartItem.lineItems[0].price!!.toInt()))
        }
        binding.subTotalValueTx.text = subTotal.toString()
    }

    private fun showSupportBottomSheet() {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.buttom_sheet_coupons, null)

        val recycleView = view.findViewById<RecyclerView>(R.id.recycleView_coupons)
        couponsAdapter = CouponsAdapter(this, this)

        cartViewModel.allDiscountCode.observe(this){
            codeList = it
            Log.d("discountCodeAct", it.size.toString())
            couponsAdapter.setData(it)
            recycleView.adapter = couponsAdapter
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onClick(code: String, limit: String) {
        dialog.cancel()
        Toast.makeText(this, "Copied!", Toast.LENGTH_LONG).show()
        binding.couponEditText.setText(code)
        discountLimit = limit
    }
}