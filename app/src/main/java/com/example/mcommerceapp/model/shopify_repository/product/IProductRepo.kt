package com.example.mcommerceapp.model.shopify_repository.product

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products

interface ICollectionsRepo {
    suspend fun getSmartCollections()
    suspend fun getSubCollection(fields:String)
}

interface IProductsRepo {
    suspend fun getAllProducts()
}

interface IProductDetailRepo {
    suspend fun getProductDetail(id: String): Products

}

interface ICategoryRepo {

    suspend fun getCollectionId(id :String): ArrayList<CustomCollections>
    suspend fun getProductsCollection(collectionId :String) :ArrayList<Products>
    suspend fun  getProductsVendor(vendor:String) :ArrayList<Products>

}

