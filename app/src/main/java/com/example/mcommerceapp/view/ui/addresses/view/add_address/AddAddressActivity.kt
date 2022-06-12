package com.example.mcommerceapp.view.ui.addresses.view.add_address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mcommerceapp.databinding.ActivityAddAddressBinding

class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmAddAddress.setOnClickListener {
            if (isValid()) {
                val intent = Intent()
                intent.putExtra("address", binding.addressEditText.text.toString())
                intent.putExtra("city", binding.cityEditText.text.toString())
                intent.putExtra("country", binding.countryEditText.text.toString())
                intent.putExtra("zip", binding.zipCodeEditText.text.toString())

                setResult(2, intent)
                finish()
            }
        }


    }

    private fun isValid(): Boolean {

        var res = true
        if(binding.addressEditText.text.toString().isEmpty()){
            binding.addressEditText.error = "not valid"
            res = false
        }
        if(binding.cityEditText.text.toString().isEmpty()){
            binding.cityEditText.error = "not valid"
            res = false
        }
        if(binding.countryEditText.text.toString().isEmpty()){
            binding.countryEditText.error = "not valid"
            res = false
        }
        if(binding.zipCodeEditText.text.toString().isEmpty()){
            binding.zipCodeEditText.error = "not valid"
            res = false
        }

        return res

    }

}