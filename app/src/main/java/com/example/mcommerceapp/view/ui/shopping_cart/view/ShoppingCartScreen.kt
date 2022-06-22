package com.example.mcommerceapp.view.ui.shopping_cart.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mcommerceadminapp.model.shopify_repository.coupon.CouponRepo
import com.example.mcommerceadminapp.pojo.coupon.discount_code.DiscountCodes
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityShoppingCartScreenBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.draft_orders_repository.DraftOrdersRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.remote_source.coupon.CouponRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.DraftOrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.payment.view.Payment
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodel
import com.example.mcommerceapp.view.ui.shopping_cart.viewmodel.ShoppingCartViewmodelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import draft_orders.DraftOrder

class ShoppingCartScreen : AppCompatActivity(), CartCommunicator {
    private lateinit var discountLimit: String
    private var isCode = false

    private lateinit var binding: ActivityShoppingCartScreenBinding

    private lateinit var cartItemsAdapter: CartItemsAdapter
    private lateinit var couponsAdapter: CouponsAdapter
    private lateinit var cartViewModel: ShoppingCartViewmodel
    private lateinit var cartViewModelFactory: ShoppingCartViewmodelFactory

    private var cartList: ArrayList<DraftOrder> = ArrayList()
    private var codeList: ArrayList<DiscountCodes> = ArrayList()
    private var user: User? = null

    private lateinit var dialog: BottomSheetDialog
    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartItemsRecyclerView.setHasFixedSize(true)
        // supportActionBar?.hide()

        loadingDialog = Dialog(this)

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
            val myTitleList = retrieveProductsTitle(cartList)
            if (!myTitleList.isNullOrEmpty()) {
                var intent = Intent(this, Payment::class.java)
                intent.putStringArrayListExtra("names_list", myTitleList)
                intent.putExtra("total_value", binding.totalValueTx.text.toString().toDouble())
                if (binding.discountValueTx.text != "0.0") {
                    intent.putExtra("coupon_code", binding.couponEditText.text.toString())
                    intent.putExtra("coupon_amount", binding.discountValueTx.text.toString())
                }
                intent.putExtra("cart_list", cartList)
                startActivityForResult(intent, PAY_INTENT_CODE)
            }
        }


        cartViewModelFactory = ShoppingCartViewmodelFactory(
            DraftOrdersRepo.getInstance(DraftOrdersRemoteSource.getInstance()),
            UserRepo.getInstance(this),
            CouponRepo.getInstance(CouponRemoteSource()),
            CurrencyRepo.getInstance(RemoteSource(), this)
        )

        cartViewModel =
            ViewModelProvider(this, cartViewModelFactory)[ShoppingCartViewmodel::
            class.java]

        cartViewModel.getUser().observe(this) {
            user = it
            if (user != null) {
                cartViewModel.getAllDraftOrders(user!!.userID)
            } else {
                print("No User Found")
            }
        }

        cartViewModel.draftOrderLiveData.observe(this)
        {
            if (it != null) {
                loadingDialog.dismiss()
                if (it.size > 0) {
                    binding.shippingValueTx.text = "30.0"
                }
                cartList = it
                binding.progressIndicator.visibility = View.INVISIBLE
                cartItemsAdapter.setOrders(cartList, cartViewModel.symbol, cartViewModel.value)
                calculateTotalAmountOfMoney()
            }
        }

        cartViewModel.updateLiveData.observe(this)
        {
            if (it != null) {
                loadingDialog.dismiss()
                finish()
            }
        }
        binding.actionBar.backImg.setOnClickListener {
            finish()
        }

        cartViewModel.getAllDiscount()
        binding.getCouponsTxt.setOnClickListener {
            showSupportBottomSheet()
        }

        binding.validationBtn.setOnClickListener {
            binding.couponEditText.isClickable = false

            for (code in codeList) {
                if (binding.couponEditText.text.toString() == code.code) {
                    isCode = true
                    binding.discountValueTx.text = discountLimit.drop(1)
                    calculateTotalAmountOfMoney()
                    binding.couponEditText.isEnabled = false
                    break
                } else {
                    binding.couponEditText.error = "Code Wrong"
                }
            }

            if (!isCode) {
                binding.couponEditText.error = "Code Wrong"
            }
        }
        binding.subTotalValueTx.text = "0.0"
        binding.discountValueTx.text = "0.0"
        binding.shippingValueTx.text = "0.0"
        binding.totalValueTx.text = "0.0"
        binding.subTotalValueTx.text = cartViewModel.symbol
        binding.discountCurrencyTx.text = cartViewModel.symbol
        binding.shippingCurrencyTx.text = cartViewModel.symbol
        binding.totalCurrencyTx.text = cartViewModel.symbol

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PAY_INTENT_CODE -> {
                val isSuccessful = data?.getBooleanExtra("isSuccessful", false)
                showLoadingDialog("Creating Order...")
                if (isSuccessful!!) {
                    cartViewModel.deleteAllOrders(cartList, user!!.userID)
                } else {
                    loadingDialog.dismiss()
                }
                binding.shippingValueTx.text = "0.0"
                binding.discountValueTx.text = "0.0"
                binding.couponEditText.setText("")
            }
        }
    }

    override fun onBackPressed() {
        showLoadingDialog("Saving...")
        cartViewModel.updateDarftOrder(cartList)
    }

    override fun calculateNewSubTotal() {
        calculateTotalAmountOfMoney()
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
        calculateTotalAmountOfMoney()
    }

    override fun decreaseUpdateInList(index: Int) {
        var newQuantity = cartList[index].lineItems[0].quantity!!
        newQuantity--
        cartList[index].lineItems[0].quantity = (newQuantity)
        calculateTotalAmountOfMoney()
    }

    override fun onClick(code: String, limit: String) {
        dialog.cancel()
        Toast.makeText(this, "Copied!", Toast.LENGTH_LONG).show()
        binding.couponEditText.setText(code)
        discountLimit = limit
    }

    private fun deleteDraftOrderFromList(draftOrder: DraftOrder) {
        cartList.remove(draftOrder)
        cartItemsAdapter.setOrders(cartList, cartViewModel.symbol, cartViewModel.value)
        if (cartList.size == 0) {
            binding.shippingValueTx.text = "0.0"
        }
        calculateTotalAmountOfMoney()
    }

    private fun calculateTotalAmountOfMoney() {
        val subTotal = calculateSubTotal()
        binding.subTotalValueTx.text = subTotal.toString()
        val total = calculateTotal(
            subTotal,
            binding.discountValueTx.text.toString().toDouble(),
            binding.shippingValueTx.text.toString().toDouble()
        )
        binding.totalValueTx.text = total.toString()
    }

    private fun calculateSubTotal(): Double {
        var subTotal = 0.0
        cartList.forEach { cartItem ->
            subTotal += (cartItem.lineItems[0].quantity!!.toInt()
                .times(cartItem.lineItems[0].price!!.toDouble()))
        }
        return subTotal
    }

    private fun calculateTotal(subTotal: Double, discount: Double, shipping: Double): Double {
        return (subTotal - discount + shipping)
    }

    private fun retrieveProductsTitle(list: ArrayList<DraftOrder>): ArrayList<String> {
        var titleList: ArrayList<String> = ArrayList()
        list.forEach {
            titleList.add(it.lineItems[0].name.toString())
        }
        return titleList
    }

    private fun showSupportBottomSheet() {
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.buttom_sheet_coupons, null)

        val recycleView = view.findViewById<RecyclerView>(R.id.recycleView_coupons)
        couponsAdapter = CouponsAdapter(this, this)

        cartViewModel.allDiscountCode.observe(this) {
            codeList = it
            Log.d("discountCodeAct", it.size.toString())
            couponsAdapter.setData(it)
            recycleView.adapter = couponsAdapter
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showLoadingDialog(string: String) {
//        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCancelable(false)

        loadingDialog.setContentView(R.layout.dialog_wait_to_finish)
        loadingDialog.findViewById<TextView>(R.id.loading_tx).text = string

        loadingDialog.show()
    }

    companion object {
        const val PAY_INTENT_CODE = 144
    }
}