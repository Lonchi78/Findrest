package com.lonchi.andrej.findrest.Data

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.lonchi.andrej.findrest.Data.db.RestaurantDao
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant


class GeocodeRepository(private val restaurantDao: RestaurantDao) {

    val allWords: LiveData<List<Restaurant>> = restaurantDao.getAllRestaurants()

    @WorkerThread
    suspend fun upsert(restaurant: Restaurant) {
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun getFirstRestaurant(): Restaurant{
        return restaurantDao.getFirstRestaurant()
    }
}