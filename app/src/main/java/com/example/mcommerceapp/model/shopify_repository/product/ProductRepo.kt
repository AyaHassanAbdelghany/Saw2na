package com.example.mcommerceapp.model.shopify_repository.product

import androidx.lifecycle.MutableLiveData
import com.example.mcommerceapp.model.remote_source.products.IProductRemoteSource
import com.example.mcommerceapp.model.remote_source.products.ProductRemoteSource
import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class ProductRepo private  constructor(private var remoteSource : IProductRemoteSource): ICollectionsRepo,IProductsRepo, IProductDetailRepo, ICategoryRepo{

    companion object {
        private val productRepo: ProductRepo? = null
        val vendors = MutableLiveData<HashSet<SmartCollections>>()
        val allProducts = MutableLiveData<ArrayList<Products>>()
        val subCollections = MutableLiveData<HashSet<ProductFields>>()


        fun getInstance(remoteSource: IProductRemoteSource): ProductRepo {
            return productRepo ?: ProductRepo(remoteSource)
        }
    }


    override suspend fun getAllProducts() {
        allProducts.postValue(remoteSource.getAllProducts())
    }

    override suspend fun getCollectionId(id: String): ArrayList<CustomCollections> {
        return remoteSource.getCollectionId(id)
    }


    override suspend fun getProductsCollection(collectionId: String): ArrayList<Products> {
       return remoteSource.getProductsCollection(collectionId)
    }

    override suspend fun getProductsVendor(vendor: String): ArrayList<Products> {
        return remoteSource.getProductsVendor(vendor)
    }

    override suspend fun getSmartCollections() {
        vendors.postValue(remoteSource.getSmartCollections())
    }

    override suspend fun getSubCollection(fields: String) {
        subCollections.postValue(remoteSource.getSubCollections(fields))
    }

    override suspend fun getProductDetail(id: String): Products{
        return remoteSource.getProductDetail(id)
    }
}