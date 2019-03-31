package com.lonchi.andrej.findrest


import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.lonchi.andrej.findrest.Data.ZomatoApiService
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = "https://developers.zomato.com/api/v2.1/geocode?lat=49.195061&lon=16.606836"
        val lat = 49.195061
        val lon = 16.606836
        val restaurantId = 16507359

        //  Get api
        val mApiService = ZomatoApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val restaurant = mApiService.getRestaurant(restaurantId)
            val response = restaurant.await()
            d("Zomato", response.toString())
            d("Zomato Name", response.name.toString())
        }

        //  On button Search click
        btn_search.setOnClickListener{
            it.findNavController().navigate(R.id.actionToSearch)
        }

        //  On button Favourites click
        btn_favourites.setOnClickListener{
            it.findNavController().navigate(R.id.actionToFavourites)
        }
    }

}
