package com.example.mcommerceapp.model.addresses_repository

import com.example.mcommerceapp.model.remote_source.addresses.AddressesRemoteSource
import com.example.mcommerceapp.pojo.customers.Addresses
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AddressesRepo private constructor(private val remoteSource: AddressesRemoteSource) {

    companion object {
        private val addressesRepo: AddressesRepo? = null
        fun getInstance(remoteSource: AddressesRemoteSource): AddressesRepo {
            return addressesRepo ?: AddressesRepo(remoteSource)
        }
    }

    suspend fun addNewAddress(id : String,req: Addresses): Addresses {
       return remoteSource.addNewAddress(id,getRequest(req))
    }

    suspend fun updateAddress(customerID:String,addressID :String,req: Addresses): Addresses {
        return remoteSource.updateAddress(customerID,addressID,getRequest(req))
    }

    suspend fun getAddressByCustomerID(customerID:String): ArrayList<Addresses> {
      return remoteSource.getAddressByCustomerID(customerID)
    }

    suspend fun setDefaultAddress(customerID:String,addressID :String) {
        remoteSource.setDefaultAddress(customerID,addressID)
    }

    suspend fun deleteAddressByID(customerID:String,addressID :String) {
        remoteSource.deleteAddressByID(customerID,addressID)
    }


    private fun getRequest(address: Addresses): RequestBody {

        val jsonReq = JSONObject()
        jsonReq.put("address1",address.address1)
        jsonReq.put("city",address.city)
        jsonReq.put("country",address.country)
        jsonReq.put("zip",address.zip)

        val req = JSONObject()
        req.put("address", jsonReq)

        return req.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }


}