package com.example.mcommerceapp.view.ui.addresses.view.adapter

interface AddressesCommunicator {
    fun setDefaultAddress(addressID:String)
    fun deleteAddress(addressID:String)
}