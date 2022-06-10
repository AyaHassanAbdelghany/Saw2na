package com.example.mcommerceapp.view.ui.addresses.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityAddressesBinding
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.remote_source.addresses.AddressesRemoteSource
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.view.ui.addresses.add_address.AddAddressActivity
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesAdapter
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesCommunicator
import com.example.mcommerceapp.view.ui.addresses.view_model.AddressesViewModel
import com.example.mcommerceapp.view.ui.addresses.view_model.factory.AddressesViewModelFactory

class AddressesActivity : AppCompatActivity() , AddressesCommunicator{
    lateinit var binding: ActivityAddressesBinding
    lateinit var viewModel: AddressesViewModel
    lateinit var vmFactory: AddressesViewModelFactory
    var customerID:String = "5759375868043"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vmFactory = AddressesViewModelFactory(AddressesRepo(AddressesRemoteSource()))
        viewModel = ViewModelProvider(this, vmFactory)[AddressesViewModel::class.java]

        val orderAdapter = AddressesAdapter(this,this)

        orderAdapter.setData(ArrayList())
        binding.recycleViewAddress.adapter = orderAdapter

        binding.addAddressButton.setOnClickListener {
            startActivityForResult(Intent(this,AddAddressActivity::class.java),2)
        }

        viewModel.getAddressByCustomerID("5759375868043")
        viewModel.addresses.observe(this){
            orderAdapter.setData(it)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2){

            val address = Addresses()
            if (data != null) {
                address.address1 = data.getStringExtra("address")
                address.city = data.getStringExtra("city")
                address.country = data.getStringExtra("country")
                address.zip = data.getStringExtra("zip")

                viewModel.addNewAddress(customerID,address)

            }


        }
    }

    override fun setDefaultAddress(addressID: String) {
        Log.i("address", "setDefaultAddress: $addressID ")
        viewModel.setDefaultAddress(customerID,addressID)
    }

    override fun deleteAddress(addressID: String) {
        Log.i("address", "deleteAddress: $addressID ")
        viewModel.deleteAddressByID(customerID,addressID)
    }

}