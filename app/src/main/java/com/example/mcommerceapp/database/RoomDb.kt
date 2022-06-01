package com.example.mcommerceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mcommerceapp.pojo.favorite_products.FavProducts

@Database(entities = [FavProducts::class], version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract fun roomDAO(): RoomDAO

    companion object {
        private var INSTANCE: RoomDb? = null

        //one thread at a time to access this method
        @Synchronized
        fun getInstance(context: Context): RoomDb {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java,
                "ProductsAppData"
            ).fallbackToDestructiveMigration().build()
        }
    }
}