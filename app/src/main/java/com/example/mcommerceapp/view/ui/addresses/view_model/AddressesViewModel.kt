package com.example.mcommerceapp.view.ui.addresses.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.addresses_repository.AddressesRepo
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.pojo.customers.Addresses
import com.example.mcommerceapp.pojo.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddressesViewModel(private val addressesRepo: AddressesRepo, private val userRepo: UserRepo) :
    ViewModel() {
    private val _addresses = MutableLiveData<ArrayList<Addresses>>()
    val addresses: LiveData<ArrayList<Addresses>> = _addresses


    fun getAddressByCustomerID(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val c = addressesRepo.getAddressByCustomerID(id)
            withContext(Dispatchers.Main) {
                _addresses.value = c
            }
        }
    }

    fun addNewAddress(customerID: String, req: Addresses) {
        viewModelScope.launch(Dispatchers.IO) {
            addressesRepo.addNewAddress(customerID, req)
            getAddressByCustomerID(customerID)
        }
    }

    fun updateAddress(customerID: String, addressID: String, req: Addresses) {
        viewModelScope.launch(Dispatchers.IO) {
            addressesRepo.updateAddress(customerID, addressID, req)
            getAddressByCustomerID(customerID)
        }
    }

    fun setDefaultAddress(customerID: String, addressID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressesRepo.setDefaultAddress(customerID, addressID)
            getAddressByCustomerID(customerID)
        }
    }

    fun deleteAddressByID(customerID: String, addressID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressesRepo.deleteAddressByID(customerID, addressID)
            getAddressByCustomerID(customerID)
        }
    }

    fun retrieveUserFromFireStore(): LiveData<User> {
        return userRepo.retrieveUserFromFireStore()
    }
}