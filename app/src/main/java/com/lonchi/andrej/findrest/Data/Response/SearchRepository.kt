package com.lonchi.andrej.findrest.Data.Response

import android.util.Log
import android.util.Log.d
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.findrest.Data.ZomatoApiService
import com.lonchi.andrej.findrest.Data.db.RestaurantDao
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import com.lonchi.andrej.findrest.Data.db.entity.Restaurants
import java.lang.StringBuilder


class SearchRepository(private val restaurantDao: RestaurantDao) {

    val TAG = "Repository"

    val foundRestaurants = MutableLiveData<List<Restaurant>>()

    val zomatoApiService = ZomatoApiService()

    @WorkerThread
    suspend fun upsert(restaurant: Restaurant) {
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun delete(restaurant: Restaurant) {
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun deleteAll() {
        restaurantDao.deleteAll()
    }

    @WorkerThread
    suspend fun executeSearch(query: String?) {
        //  Get response
        val searchResponse = zomatoApiService.getSearch(query.toString())
        val searchObject = searchResponse.await()

        val mutableRestaurantList = mutableListOf<Restaurant>()
        for (item in searchObject.restaurants){
            upsert(item.restaurant)
            mutableRestaurantList.add(item.restaurant)
            Log.d(TAG, "F: ${item.restaurant.name}")
        }
        val restaurantList: List<Restaurant> = mutableRestaurantList


        foundRestaurants.postValue(restaurantList)
        Log.d("FUCK", foundRestaurants.toString())
    }

    @WorkerThread
    suspend fun executeGeocode(lat: Double, lon: Double) {
        //  Get response
        val geocodeResponse = zomatoApiService.getGeocode(lat, lon)
        val geocodeObject = geocodeResponse.await()

        val mutableRestaurantList = mutableListOf<Restaurant>()
        for (item in geocodeObject.nearbyRestaurants){
            upsert(item.restaurant)
            mutableRestaurantList.add(item.restaurant)
            Log.d(TAG, "F: ${item.restaurant.name}")
        }
        val restaurantList: List<Restaurant> = mutableRestaurantList


        foundRestaurants.postValue(restaurantList)
        Log.d("FUCK", foundRestaurants.toString())
    }

}