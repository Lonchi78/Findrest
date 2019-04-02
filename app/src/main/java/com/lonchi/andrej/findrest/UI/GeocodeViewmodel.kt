package com.lonchi.andrej.findrest.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lonchi.andrej.findrest.Data.GeocodeRepository
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class GeocodeViewmodel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: GeocodeRepository
    val allWords: LiveData<List<Restaurant>>

    init {
        val wordsDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = GeocodeRepository(wordsDao)
        allWords = repository.allWords
    }

    fun upsert(restaurant: Restaurant) = scope.launch(Dispatchers.IO) {
        repository.upsert(restaurant)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}