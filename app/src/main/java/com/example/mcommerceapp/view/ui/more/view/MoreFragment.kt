package com.example.mcommerceapp.view.ui.more.view

import android.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentMoreBinding
import com.example.mcommerceapp.model.shopify_repository.currency.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.model.shopify_repository.user.UserRepo
import com.example.mcommerceapp.network.MyConnectivityManager
import com.example.mcommerceapp.view.ui.addresses.view.AddressesActivity
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.more.view_model.MoreViewModel
import com.example.mcommerceapp.view.ui.more.view_model.factory.MoreViewModelFactory


class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: MoreViewModel
    private var currencyArray: List<String> = ArrayList()
    private val languagesArray = arrayOf("en", "ar")
    private val languagesArray2 = arrayOf("EN", "AR")


    private lateinit var currencySpinner: Spinner
    private lateinit var languageSpinner: Spinner

    private var isLoggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
            isLoggedIn = false
            binding.signOutButton.visibility = View.INVISIBLE
        }
        var sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("user", 0)
        val lang = sharedPreferences.getString("lan", "en")
        sharedPreferences = requireContext().getSharedPreferences("currency", 0)
        val cur = sharedPreferences.getString("symbol", "EGP")


        var currencyAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, currencyArray)
        currencyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val languageAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, languagesArray2)
        languageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = languageAdapter

        languageSpinner.setSelection(languagesArray.indexOf(lang))

        binding.saveSettingsButton.setOnClickListener {
            val currency = currencyArray[currencySpinner.selectedItemPosition]
            viewModel.convert(currency)

            val lang = languagesArray[languageSpinner.selectedItemPosition]
            viewModel.setLanguage(lang)

            requireActivity().finish()
            requireActivity().startActivity(requireActivity().intent)

        }

        viewModel.getCurrencySymbols()

        viewModel.symbols.observe(viewLifecycleOwner) {
            this.currencyArray = it.symbols.keys.toList()
            currencyAdapter = ArrayAdapter<String>(
                this@MoreFragment.requireContext(),
                R.layout.simple_spinner_item,
                this.currencyArray
            )
            currencyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            currencySpinner.adapter = currencyAdapter
            currencySpinner.setSelection(this.currencyArray.indexOf(cur))
        }

        binding.shippingAddressesLayout.setOnClickListener {
            if (isLoggedIn) {
                startActivity(Intent(requireContext(), AddressesActivity::class.java))
            } else {
                startActivity(Intent(requireContext(), SigninActivity::class.java))
            }
        }

        binding.languageSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (currencyArray.isNotEmpty())
                if (languagesArray[position] == lang && currencyArray[currencySpinner.selectedItemPosition] == cur )
                    binding.saveSettingsButton.visibility = View.INVISIBLE
                else
                    binding.saveSettingsButton.visibility = View.VISIBLE

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        binding.currencySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (currencyArray[position] == cur && languagesArray[languageSpinner.selectedItemPosition] == lang)
                    binding.saveSettingsButton.visibility = View.INVISIBLE
                else
                    binding.saveSettingsButton.visibility = View.VISIBLE

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        isLoggedIn = viewModel.isLogged()
        if (isLoggedIn) {
            binding.signOutButton.visibility = View.VISIBLE
        } else {
            binding.signOutButton.visibility = View.INVISIBLE
        }
    }

    private fun init() {
        languageSpinner = binding.languageSpinner
        currencySpinner = binding.currencySpinner

        val viewModelFactory = MoreViewModelFactory(
            UserRepo.getInstance(
                requireContext()
            ), CurrencyRepo.getInstance(
                ProductRemoteSource.getInstance(), requireContext()
            )
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[MoreViewModel::class.java]

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.symbols.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}