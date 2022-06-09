package com.example.mcommerceapp.view.ui.addresses.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.ActivityAddressesBinding
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesAdapter
import com.example.mcommerceapp.view.ui.addresses.view.adapter.AddressesCommunicator
import com.example.mcommerceapp.view.ui.addresses.view_model.AddressesViewModel
import com.example.mcommerceapp.view.ui.addresses.view_model.factory.AddressesViewModelFactory

class AddressesActivity : AppCompatActivity() , AddressesCommunicator{
    lateinit var binding: ActivityAddressesBinding
    lateinit var orderVM: AddressesViewModel
    lateinit var orderVMFactory: AddressesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderVMFactory = AddressesViewModelFactory()
        orderVM = ViewModelProvider(this, orderVMFactory)[AddressesViewModel::class.java]

        val orderAdapter = AddressesAdapter(this,this)

        orderAdapter.setData(getList())
        binding.recycleViewAddress.adapter = orderAdapter




    }

    private fun getList():ArrayList<Addresses>{
        val orderList: ArrayList<Addresses> = arrayListOf()

        var ad = Addresses()
        ad.city = "Alex"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)

        ad = Addresses()
        ad.city = "Abc"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)

        ad = Addresses()
        ad.city = "Aoooo"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)

        ad = Addresses()
        ad.city = "yAhooo"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)
        ad = Addresses()
        ad.city = "yAhooo"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)

        ad = Addresses()
        ad.city = "yAhooo"
        ad.country = "Egy"
        ad.zip = "5845"
        orderList.add(ad)



        return orderList
    }
}