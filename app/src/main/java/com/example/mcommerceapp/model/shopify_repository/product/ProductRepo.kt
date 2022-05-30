package com.example.mcommerceapp.model.shopify_repository.product

import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class ProductRepo private  constructor(private var remoteSource : RemoteSource): IProducts{

    companion object {
        private val productRepo: ProductRepo? = null

        fun getInstance(remoteSource: RemoteSource): ProductRepo {
            return productRepo ?: ProductRepo(remoteSource)
        }
    }

    override suspend fun getProducts(): ArrayList<Products> {
       return remoteSource.getAllProducts()
    }

    override suspend fun getProductsTypes(fields: String): ArrayList<ProductFields> {
        return remoteSource.getProductsTypes(fields)
    }

    override suspend fun getSmartCollections(): ArrayList<SmartCollections> {
        return remoteSource.getSmartCollections()
    }

}