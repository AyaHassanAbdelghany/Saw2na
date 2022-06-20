package com.example.mcommerceapp.view.ui.more.view

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentMoreBinding
import com.example.mcommerceapp.model.currency_repository.CurrencyRepo
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.addresses.view.AddressesActivity
import com.example.mcommerceapp.view.ui.more.view_model.MoreViewModel
import com.example.mcommerceapp.view.ui.more.view_model.factory.MoreViewModelFactory
import java.util.*
import kotlin.collections.ArrayList


class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: MoreViewModel
    private var currencyArray: List<String> = ArrayList()
    private val languagesArray = arrayOf("en", "ar")

    private lateinit var currencySpinner: Spinner
    private lateinit var languageSpinner: Spinner

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
            Toast.makeText(requireContext(), "Signed Out Successfully", Toast.LENGTH_SHORT).show()

        }
        var sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("user", 0)
        val lang = sharedPreferences.getString("lan", "en")
        sharedPreferences = requireContext().getSharedPreferences("currency", 0)
        val cur = sharedPreferences.getString("symbol","EGP")


        var currencyAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, currencyArray)
        currencyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val languageAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, languagesArray)
        languageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = languageAdapter
        languageSpinner.setSelection(languagesArray.indexOf(lang))

        binding.saveSettingsButton.setOnClickListener {
            val currency = currencyArray[currencySpinner.selectedItemPosition]
            viewModel.convert(currency)

            val lang = languagesArray[languageSpinner.selectedItemPosition]
            viewModel.setLanguage(lang)

            val locale = Locale(lang)
            Locale.setDefault(locale)
            val resources: Resources = resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            requireActivity().finish()
            requireActivity().startActivity(requireActivity().intent)

        }

        viewModel.getCurrencySymbols()

        viewModel.symbols.observe(viewLifecycleOwner) {

            Log.e("TAG", "observe : ")

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
            startActivity(Intent(requireContext(),AddressesActivity::class.java))
        }

    }

    private fun init() {
        languageSpinner = binding.languageSpinner
        currencySpinner = binding.currencySpinner

        val viewModelFactory = MoreViewModelFactory(
            UserRepo.getInstance(
                requireContext()
            ), CurrencyRepo.getInstance(
                RemoteSource(), requireContext()
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