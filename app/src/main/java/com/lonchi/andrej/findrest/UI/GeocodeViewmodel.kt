package com.lonchi.andrej.findrest.UI

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.findrest.Data.Repository
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
    private val repository: Repository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = Repository(restaurantDao)
    }

    fun executeGeocode(lat: Double, lon: Double) = scope.launch(Dispatchers.IO) {
        Log.d("Geocode VM", "execute geocode")

        //  Execute geocode and obtain LiveData
        repository.executeGeocode(lat, lon)
        foundRestaurants.postValue(repository.foundRestaurants.value)
        Log.d("Geocode VM", foundRestaurants.toString())
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}