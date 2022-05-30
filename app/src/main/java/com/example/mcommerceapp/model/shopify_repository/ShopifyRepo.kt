package com.example.mcommerceapp.model.shopify_repository

import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.Products

class ShopifyRepo private  constructor(private var remoteSource :RemoteSource):IProducts, ICustomCollections {

    companion object {
        val PRODUCTS = "PRODUCTS"
        val CUSTOMCOLLECTIONS = "CustomCollections"
        private val shopifyRepo: ShopifyRepo? = null

        fun getInstance(remoteSource: RemoteSource): ShopifyRepo {

                return shopifyRepo ?: ShopifyRepo(remoteSource)
        }
    }

    override suspend fun getProducts(): ArrayList<Products> {
        return remoteSource.getAllProducts()
    }

    override suspend fun getCustomCollections(): ArrayList<CustomCollections> {
      return  remoteSource.getCustomCollections()
    }

}