package com.lonchi.andrej.findrest.Data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lonchi.andrej.findrest.Data.db.RestaurantDao
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant


class Repository(private val restaurantDao: RestaurantDao) {

    val allRestaurants: LiveData<List<Restaurant>> = restaurantDao.getAllRestaurants()

    @WorkerThread
    suspend fun upsert(restaurant: Restaurant) {
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun delete(restaurant: Restaurant) {
        restaurantDao.delete(restaurant)
    }

    @WorkerThread
    suspend fun deleteAll() {
        restaurantDao.deleteAll()
    }

}