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

class SearchViewmodel(application: Application) : AndroidViewModel(application) {

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

    fun executeSearch(query: String?) = scope.launch(Dispatchers.IO) {
        Log.d("Search VM", "execute search")

        //  Execute search and obtain LiveData
        repository.executeSearch(query)
        foundRestaurants.postValue(repository.foundRestaurants.value)
        Log.d("Search VM", foundRestaurants.toString())
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}