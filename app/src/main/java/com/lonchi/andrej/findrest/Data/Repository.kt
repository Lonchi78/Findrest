package com.lonchi.andrej.findrest.Data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.findrest.Data.db.RestaurantDao
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant


class Repository(private val restaurantDao: RestaurantDao) {

    //  Api Service
    val zomatoApiService = ZomatoApiService()

    //  Use Offline Fragment
    val allRestaurants: LiveData<List<Restaurant>> = restaurantDao.getAllRestaurants()

    //  Use Search and Geocode Fragment
    val foundRestaurants = MutableLiveData<List<Restaurant>>()


    @WorkerThread
    suspend fun upsert(restaurant: Restaurant) {
        //  Insert or Update restaurant
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun delete(restaurant: Restaurant) {
        //  Delete one restaurant
        restaurantDao.upsert(restaurant)
    }

    @WorkerThread
    suspend fun deleteAll() {
        //  Delete all restaurants
        restaurantDao.deleteAll()
    }

    @WorkerThread
    suspend fun getRestaurant(res_id: String): Restaurant {
        //  Get restaurant
        return restaurantDao.getRestaurantById(res_id)
    }

    @WorkerThread
    suspend fun executeSearch(query: String?) {
        //  Get response
        val searchResponse = zomatoApiService.getSearch(query.toString())
        val searchObject = searchResponse.await()

        //  List<Restaurants>   =>  List<Restaurant>
        //  Store Restauratn to Database
        val mutableRestaurantList = mutableListOf<Restaurant>()
        for (item in searchObject.restaurants){
            Log.d("Repository", "Found restaurant ${item.restaurant.name}")
            upsert(item.restaurant)
            mutableRestaurantList.add(item.restaurant)
        }
        val restaurantList: List<Restaurant> = mutableRestaurantList

        //  Set LiveData
        foundRestaurants.postValue(restaurantList)
        Log.d("Repository", "Live data ${foundRestaurants.toString()}")
    }

    @WorkerThread
    suspend fun executeGeocode(lat: Double, lon: Double) {
        //  Get response
        val geocodeResponse = zomatoApiService.getGeocode(lat, lon)
        val geocodeObject = geocodeResponse.await()

        //  List<NearbyRestaurants>   =>  List<Restaurant>
        //  Store Restaurant to Database
        val mutableRestaurantList = mutableListOf<Restaurant>()
        for (item in geocodeObject.nearbyRestaurants){
            Log.d("Repository", "Found restaurant ${item.restaurant.name}")
            upsert(item.restaurant)
            mutableRestaurantList.add(item.restaurant)
        }
        val restaurantList: List<Restaurant> = mutableRestaurantList

        //  Set LiveData
        foundRestaurants.postValue(restaurantList)
        Log.d("Repository", "Live data ${foundRestaurants.toString()}")
    }

}