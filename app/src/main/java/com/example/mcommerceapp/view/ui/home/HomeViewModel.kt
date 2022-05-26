package com.example.mcommerceapp.view.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.model.shopify_repository.ShopifyRepo
import com.example.mcommerceapp.pojo.products.Products
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<List<Products>>()
    val text: LiveData<List<Products>> = _text

    fun getProduct() {

        viewModelScope.launch {
//            val s: ShopifyService =
//                ShopifyRetrofitHelper.getInstance().create(ShopifyService::class.java)
//            val res = s.getProducts("products.json")
//            val gson = Gson()

            val d = ShopifyRepo.getInstance(RemoteSource())
            Log.d("RepoProduct", d.getCustomCollections()[0].title!!)
//            val p = d.getAllProducts()
//            Log.i("TAG", "getProduct:  ${p.count()}")

            //  var jsonObject = gson.toJsonTree(res).asJsonObject;

            //   var parsed = res.body() as LinkedTreeMap<String, List<Products>>
//            Log.d("parsed", res.body().toString())
//
//           // var x1 = parsed["products"].toString().replace("\\0"," ")
//            var x :List<Products> = gson.fromJson( res.body()!!.get("products") , Array<Products>::class.java).toList()
//            Log.d("x", x.count().toString())
            //var result = gson.fromJson( x ,Products::class.java) as ArrayList<Products>
            //_text.postValue(res.body()!!.products)
        }
    }
}