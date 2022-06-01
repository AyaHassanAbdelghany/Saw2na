package com.example.mcommerceapp.model.shopify_repository.product

import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

//interface IProducts {
//
//    suspend fun getProducts() :ArrayList<Products>
//    suspend fun getCategoryForCollection(fields:String,collectionId :String): HashSet<ProductFields>
//    suspend fun getCategoryForVendor(fields:String,collectionId :String,vendor:String): HashSet<ProductFields>
//    suspend fun getSubCollection(fields: String): HashSet<ProductFields>
//
//    suspend fun getCustomCollections(): ArrayList<CustomCollections>
//
//    suspend fun getSmartCollections(): ArrayList<SmartCollections>
//
//    suspend fun getProductDetail(id: Long): Products
//
//}

interface CollectionsRepo {
    suspend fun getProducts(): ArrayList<Products>

    suspend fun getSmartCollections(): ArrayList<SmartCollections>

    suspend fun getSubCollection(fields: String): HashSet<ProductFields>

}


interface ProductDetailRepo {
    suspend fun getProductDetail(id: Long): Products
}

interface CategoryRepo {
    suspend fun getCategoryForVendor(
        fields: String,
        collectionId: String,
        vendor: String
    ): HashSet<ProductFields>

    suspend fun getCategoryForCollection(
        fields: String,
        collectionId: String
    ): HashSet<ProductFields>

}

interface CustomCollectionsRepo {
    suspend fun getCustomCollections(): ArrayList<CustomCollections>
}