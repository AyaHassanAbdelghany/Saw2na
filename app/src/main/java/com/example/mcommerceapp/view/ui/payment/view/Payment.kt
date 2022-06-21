package com.example.mcommerceapp.view.ui.payment.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivityPaymentBinding
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.orders_repository.OrdersRepo
import com.example.mcommerceapp.model.remote_source.addresses.AddressesRemoteSource
import com.example.mcommerceapp.model.remote_source.orders.OrdersRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.orders.DiscountCodes
import com.example.mcommerceapp.pojo.orders.ShippingAddress
import com.example.mcommerceapp.pojo.user.User
import com.example.mcommerceapp.view.ui.addresses.view.AddressesActivity
import com.example.mcommerceapp.view.ui.payment.viewmodel.CheckoutViewModel
import com.example.mcommerceapp.view.ui.payment.viewmodel.PaymentViewmodel
import com.example.mcommerceapp.view.ui.payment.viewmodel.PaymentViewmodelFactory
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.wallet.PaymentData
import draft_orders.DraftOrder
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Payment : AppCompatActivity() {
    private val model: CheckoutViewModel by viewModels()

    private lateinit var layout: ActivityPaymentBinding
    private lateinit var googlePayButton: View

    private var namesList: ArrayList<String> = ArrayList()
    private var total: Double = 0.0
    private var couponAmount = ""
    private var couponCode: String = ""

    private var isSuccessful = false

    private lateinit var paymentViewmodel: PaymentViewmodel
    private lateinit var paymentViewmodelFactory: PaymentViewmodelFactory

    private lateinit var user: User
    private lateinit var shippingAddress: ShippingAddress
    private var discountCodes = DiscountCodes()
    private var discountCodesList: ArrayList<DiscountCodes> = ArrayList()
    private var cartList: ArrayList<DraftOrder> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use view binding to access the UI elements
        layout = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(layout.root)

        namesList = intent.getStringArrayListExtra("names_list") as ArrayList<String>
        total = intent.getDoubleExtra("total_value", 0.0)
        couponCode = intent.getStringExtra("coupon_code") ?: ""
        couponAmount = intent.getStringExtra("coupon_amount") ?: ""
        cartList = intent.getSerializableExtra("cart_list") as ArrayList<DraftOrder>

        if (couponCode.isNotEmpty() && couponAmount.isNotEmpty()) {
            discountCodes.type = "fixed_amount"
            discountCodes.amount = couponAmount
            discountCodes.code = couponCode

            discountCodesList.add(discountCodes)
        }

        Log.e("TAG", "onCreate: Marwan ${discountCodesList.size}")

        val purchaseDetails = StringBuilder()
        namesList.forEach {
            purchaseDetails.append("(${it}) , ")
        }

        layout.purchaseDescriptionTx.text = purchaseDetails
        layout.amountValueTx.text = total.toString()

        layout.dateTx.text = SimpleDateFormat("d MMMM, yyyy").format(Date())
        layout.timeTx.text = SimpleDateFormat("hh:mm aa").format(Date())

        layout.changeAddress.setOnClickListener {
            startActivity(Intent(this, AddressesActivity::class.java))
        }

        layout.cancel.setOnClickListener {
            val newIntent = Intent()
            newIntent.putExtra("isSuccessful", isSuccessful)
            setResult(ShoppingCartScreen.PAY_INTENT_CODE, newIntent)
            finish()
        }

        // Setup buttons
        googlePayButton = layout.googlePayButton.root
        googlePayButton.setOnClickListener { requestPayment(total = total) }

        // Check Google Pay availability
        model.canUseGooglePay.observe(this, Observer(::setGooglePayAvailable))

        paymentViewmodelFactory = PaymentViewmodelFactory(
            OrdersRepo.getInstance(OrdersRemoteSource()),
            AddressesRepo.getInstance(AddressesRemoteSource()),
            UserRepo.getInstance(this)
        )

        paymentViewmodel =
            ViewModelProvider(this, paymentViewmodelFactory)[PaymentViewmodel::class.java]

        paymentViewmodel.getUser().observe(this) {
            user = it
            if (it != null) {
                paymentViewmodel.getAddress(user)
            }
        }

        paymentViewmodel.addresses.observe(this) {
            shippingAddress = ShippingAddress(
                address1 = it.address1,
                city = it.city,
                country = it.country,
                zip = it.zip
            )
            layout.addressValueTx.text =
                "${shippingAddress.address1}, ${shippingAddress.city}, ${shippingAddress.country}"
        }
    }

    /**
     * If isReadyToPay returned `true`, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns `false`.
     *
     * @param available isReadyToPay API response.
     */
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                R.string.google_pay_status_unavailable,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun requestPayment(total: Double) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val task = model.getLoadPaymentDataTask(total)

        task.addOnCompleteListener { completedTask ->
            if (completedTask.isSuccessful) {
                completedTask.result.let(::handlePaymentSuccess)
            } else {
                layout.statusImg.setImageResource(R.drawable.unpaid)
                layout.googlePayButton.root.visibility = View.VISIBLE
                layout.seperatorLineConstraint.visibility = View.VISIBLE

                when (val exception = completedTask.exception) {
                    is ResolvableApiException -> {
                        resolvePaymentForResult.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    }
                    is ApiException -> {
                        handleError(exception.statusCode, exception.message)
                    }
                    else -> {
                        handleError(
                            CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                                    " exception when trying to deliver the task result to an activity!"
                        )
                    }
                }
            }

            // Re-enables the Google Pay payment button.
            googlePayButton.isClickable = true
        }
    }

    // Handle potential conflict from calling loadPaymentData
    private val resolvePaymentForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK ->
                    result.data?.let { intent ->
                        PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                    }

                RESULT_CANCELED -> {
                    // The user cancelled the payment attempt
                    layout.statusImg.setImageResource(R.drawable.unpaid)
                }
            }
        }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see [Payment
     * Data](https://developers.google.com/pay/api/android/reference/object.PaymentData)
     */
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson()

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)

            layout.statusImg.setImageResource(R.drawable.paid)

            Toast.makeText(
                this,
                getString(R.string.payments_show_name, billingName),
                Toast.LENGTH_LONG
            ).show()

            layout.googlePayButton.root.visibility = View.INVISIBLE
            layout.seperatorLineConstraint.visibility = View.INVISIBLE
            layout.cancel.text = "Back"

            isSuccessful = true

            paymentViewmodel.createOrder(
                draftOrders = cartList,
                shippingAddress = shippingAddress,
                user = user,
                discountCodesList
            )

            // Logging token string.
            Log.d(
                "Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
            )

        } catch (error: JSONException) {
            Log.e("handlePaymentSuccess", "Error: $error")
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int, message: String?) {
        Log.e("Google Pay API error", "Error code: $statusCode, Message: $message")
    }
}
