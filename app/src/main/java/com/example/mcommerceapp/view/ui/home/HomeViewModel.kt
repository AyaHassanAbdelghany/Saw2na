package com.example.mcommerceapp.view.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.network.ShopifyRetrofitHelper
import com.example.mcommerceapp.network.ShopifyService
import com.example.mcommerceapp.pojo.products.ProductResponse
import com.example.mcommerceapp.pojo.products.Products
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.ArrayList


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<List<Products>>()
    val text: LiveData<List<Products>> = _text

    fun  getProduct(){

        viewModelScope.launch {
            val s: ShopifyService =
                ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)
            val res = s.getProducts("products.json")
            Log.d("parsed", res.products?.get(0)?.title.toString())
//            val gson = Gson()
//            //_text.postValue(gson.fromJson<>(res.body(),Products::class.java))
//            var parsed = res.body() as LinkedTreeMap<String, List<Products>>
//            Log.d("parsed",parsed.get("products").toString())
//            var x = gson.fromJson( parsed.toString() ,Products::class.java)
//            Log.d("x", x.title!!)
//

            //var result = gson.fromJson( x ,Products::class.java) as ArrayList<Products>
            //_text.postValue(res.body()!!.products)
        }
    }
}