package com.lonchi.andrej.findrest.Data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1
)
abstract class RestaurantDatabase : RoomDatabase(){

    abstract fun RestaurantDao(): RestaurantDao

    //  Singleton
    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurant.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}