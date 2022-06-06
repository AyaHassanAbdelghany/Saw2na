package com.example.mcommerceapp.model.shopify_repository.product

import com.example.mcommerceapp.pojo.customcollections.CustomCollections
import com.example.mcommerceapp.pojo.products.ProductFields
import com.example.mcommerceapp.pojo.products.Products

interface CollectionsRepo {
    suspend fun getSmartCollections()
    suspend fun getSubCollection(fields: String)

}

interface ProductsRepo {
    suspend fun getAllProducts()
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