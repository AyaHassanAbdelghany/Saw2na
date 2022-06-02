package com.example.mcommerceapp.model.shopify_repository.product

import android.util.Log
import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class ProductRepo private  constructor(private var remoteSource : RemoteSource): CollectionsRepo, CustomCollectionsRepo, ProductDetailRepo, CategoryRepo, ProductTypeRepo{

    companion object {
        private val productRepo: ProductRepo? = null

        fun getInstance(remoteSource: RemoteSource): ProductRepo {
            return productRepo ?: ProductRepo(remoteSource)
        }
    }

    override suspend fun getProductCollection(productType: String, collectionId: String): ArrayList<Products> {
        return remoteSource.getProductCollection(productType, collectionId)
    }

    override suspend fun getProductVendor(productType: String, vendor: String, collectionId: String): ArrayList<Products> {
        return remoteSource.getProductVendor(productType, vendor, collectionId)
    }

    override suspend fun getCategoryForCollection(fields: String,collectionId :String): HashSet<ProductFields> {
        return remoteSource.getCategoryForCollection(fields,collectionId)
    }

    override suspend fun getSubCollection(fields: String): HashSet<ProductFields> {
        return remoteSource.getSubCollections(fields)
    }

    override suspend fun getCategoryForVendor(
        fields: String,
        collectionId: String,
        vendor: String
    ): HashSet<ProductFields> {
        Log.e("repoID", collectionId)
        return remoteSource.getCategoryForVendor(fields,collectionId,vendor)
    }



    override suspend fun getCustomCollections(): ArrayList<CustomCollections> {
        return remoteSource.getCustomCollections()
    }

    override suspend fun getSmartCollections(): ArrayList<SmartCollections> {
        return remoteSource.getSmartCollections()
    }

    override suspend fun getProductDetail(id: String): Products{
        return remoteSource.getProductDetail(id)
    }
}