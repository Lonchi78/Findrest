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

class RestaurantViewmodel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: Repository

    //  LiveData for API response
    var oneRestaurant = MutableLiveData<Restaurant>()

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = Repository(restaurantDao)
    }

    fun getRestaurant(res_id: String) = scope.launch(Dispatchers.IO) {
        Log.d("Restaurant VM", "execute getRestaurant")
        oneRestaurant.postValue(repository.getRestaurant(res_id))
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}