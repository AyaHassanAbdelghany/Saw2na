package com.example.mcommerceapp.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mcommerceapp.model.shopify_repository.product.ProductRepo
import getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@Config(sdk = [30])
@RunWith(AndroidJUnit4::class)
class ProductRepoTest :TestCase(){

    private lateinit var productsRepo : ProductRepo
    private lateinit var productFakeDataSource : ProductFakeDataSource


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() {
        productFakeDataSource =  ProductFakeDataSource.getInstance()
        productsRepo = ProductRepo.getInstance(
            productFakeDataSource
        )
    }

    @Test
    fun getSmartCollections_postValueInVendors()  =  runBlocking {
        //Given repo
        productsRepo.getSmartCollections()
        // Then
        val value = ProductRepo.vendors.getOrAwaitValue()
        assertEquals(1,value.size)
    }

    @Test
    fun getAllProductsns_postValueInAllProducts()  =  runBlocking {
        //Given repo
        productsRepo.getAllProducts()
        // Then
        val value = ProductRepo.allProducts.getOrAwaitValue()
        assertEquals(1,value.size)
    }
}