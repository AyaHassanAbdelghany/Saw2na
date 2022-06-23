package com.example.mcommerceapp.view.ui.addresses.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityAddressesBinding
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.remote_source.addresses.AddressesRemoteSource
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesAdapter
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesCommunicator
import com.example.mcommerceapp.view.ui.addresses.view.add_address.AddAddressActivity
import com.example.mcommerceapp.view.ui.addresses.view_model.AddressesViewModel
import com.example.mcommerceapp.view.ui.addresses.view_model.factory.AddressesViewModelFactory

class AddressesActivity : AppCompatActivity(), AddressesCommunicator {
    private lateinit var binding: ActivityAddressesBinding
    private lateinit var viewModel: AddressesViewModel
    private lateinit var vmFactory: AddressesViewModelFactory
    private lateinit var customerID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vmFactory = AddressesViewModelFactory(
            AddressesRepo.getInstance(AddressesRemoteSource()),
            UserRepo.getInstance(this)
        )
        viewModel = ViewModelProvider(this, vmFactory)[AddressesViewModel::class.java]

        val orderAdapter = AddressesAdapter(this, this)

        orderAdapter.setData(ArrayList())
        binding.recycleViewAddress.adapter = orderAdapter

        binding.addAddressButton.setOnClickListener {
            startActivityForResult(Intent(this, AddAddressActivity::class.java), 2)
        }

        viewModel.addresses.observe(this) {
            orderAdapter.setData(it)
            binding.recycleViewAddress.adapter = orderAdapter

        }

        viewModel.retrieveUserFromFireStore().observe(this) {
            customerID = it.userID
            binding.loadingProgressBar.visibility = View.INVISIBLE
            viewModel.getAddressByCustomerID(customerID)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {

            val address = Addresses()
            if (data != null) {
                address.address1 = data.getStringExtra("address")
                address.city = data.getStringExtra("city")
                address.country = data.getStringExtra("country")
                address.zip = data.getStringExtra("zip")

                viewModel.addNewAddress(customerID, address)

            }


        }
    }

    override fun setDefaultAddress(addressID: String) {
        Log.i("address", "setDefaultAddress: $addressID ")
        viewModel.setDefaultAddress(customerID, addressID)
    }

    override fun deleteAddress(addressID: String) {
        Log.i("address", "deleteAddress: $addressID ")
        viewModel.deleteAddressByID(customerID, addressID)
    }

}