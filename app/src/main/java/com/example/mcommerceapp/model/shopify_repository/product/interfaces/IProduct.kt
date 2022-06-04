package com.example.mcommerceapp.model.shopify_repository.product

import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

interface CollectionsRepo {

    suspend fun getSmartCollections()
    suspend fun getSubCollection(fields: String)

}


interface ProductDetailRepo {
    suspend fun getProductDetail(id: String): Products
}

interface CategoryRepo {

    suspend fun getCollectionId(title :String)
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

interface ProductTypeRepo{
    suspend fun getProductCollection(productType: String, collectionId: String): ArrayList<Products>
    suspend fun getProductVendor(productType: String, vendor: String, collectionId: String): ArrayList<Products>
}