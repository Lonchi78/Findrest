package com.lonchi.andrej.findrest.UI

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.findrest.Data.GeocodeRepository
import com.lonchi.andrej.findrest.Data.Response.SearchRepository
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GeocodeViewmodel(application: Application) : AndroidViewModel(application) {

    //  Variables for async jobs
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    //  LiveData for API response
    var foundRestaurants = MutableLiveData<List<Restaurant>>()

    //  Repository
    private val repository: SearchRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = SearchRepository(restaurantDao)
    }

    fun upsert(restaurant: Restaurant) = scope.launch(Dispatchers.IO) {
        repository.upsert(restaurant)
    }

    fun delete(restaurant: Restaurant) = scope.launch(Dispatchers.IO) {
        repository.delete(restaurant)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun executeGeocode(lat: Double, lon: Double) = scope.launch(Dispatchers.IO) {
        Log.d("FUCK", "WM exec")
        repository.executeGeocode(lat, lon)
        foundRestaurants.postValue(repository.foundRestaurants.value)
        Log.d("FUCK", "WM post exec")
        Log.d("FUCK", foundRestaurants.toString())
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}