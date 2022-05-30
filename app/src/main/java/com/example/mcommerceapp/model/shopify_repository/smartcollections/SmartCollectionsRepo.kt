package com.example.mcommerceapp.model.shopify_repository.smartcollections

import com.example.mcommerceapp.model.remote_source.RemoteSource
import com.example.mcommerceapp.pojo.smartcollections.SmartCollections

class SmartCollectionsRepo private  constructor(private var remoteSource : RemoteSource):
    ISmartCollections {

    companion object {
        private val smartCollectionsRepo: SmartCollectionsRepo? = null
        fun getInstance(remoteSource: RemoteSource): SmartCollectionsRepo {
            return smartCollectionsRepo ?: SmartCollectionsRepo(remoteSource)

        }
    }

    override suspend fun getSmartCollections(): ArrayList<SmartCollections> {
       return remoteSource.getSmartCollections()
    }
}