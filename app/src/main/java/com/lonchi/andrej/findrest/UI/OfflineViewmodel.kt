package com.lonchi.andrej.findrest.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lonchi.andrej.findrest.Data.Repository
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OfflineViewmodel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: Repository
    val allRestaurants: LiveData<List<Restaurant>>

    init {
        val wordsDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = Repository(wordsDao)
        allRestaurants = repository.allRestaurants
    }

    fun delete(restaurant: Restaurant) = scope.launch(Dispatchers.IO) {
        repository.delete(restaurant)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}