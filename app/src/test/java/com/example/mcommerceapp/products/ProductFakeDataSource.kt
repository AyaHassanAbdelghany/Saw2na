package com.example.mcommerceapp.products

import com.example.mcommerceapp.model.remote_source.products.IProductRemoteSource
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class ProductFakeDataSource private constructor() :IProductRemoteSource{

    companion object {
        private var remoteSource: ProductFakeDataSource? = null
        fun getInstance(): ProductFakeDataSource {
            return remoteSource ?: ProductFakeDataSource()
        }
    }

    override suspend fun getSmartCollections(): HashSet<SmartCollections> {
         val smartCollection = SmartCollections(title = "ADIDAS")
        var listSmartCollections : HashSet<SmartCollections> = hashSetOf(smartCollection)

        return listSmartCollections
    }

    override suspend fun getAllProducts(): ArrayList<Products> {
        val product = Products(title = "ADIDAS | CLASSIC BACKPACK")
        var listProducts : List<Products> = listOf(product)
        listProducts.let {
            return ArrayList(it)
        }
    }

    override suspend fun getProductsCollection(collectionId: String): ArrayList<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsVendor(vendor: String): ArrayList<Products> {
        TODO("Not yet implemented")
    }

    override suspend fun getCollectionId(title: String): ArrayList<CustomCollections> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubCollections(fields: String): HashSet<ProductFields> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductDetail(id: String): Products {
        TODO("Not yet implemented")
    }
}