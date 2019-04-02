package com.lonchi.andrej.findrest.UI


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import com.lonchi.andrej.findrest.Data.ZomatoApiService
import com.lonchi.andrej.findrest.R
import kotlinx.coroutines.*
import java.lang.StringBuilder


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments?.getString("searchQuery")
        tvArgument.text = argument

        //  Get api
        val mApiService = ZomatoApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val searchResponse = mApiService.getSearch(argument.toString())
            val searchObject = searchResponse.await()

            //  TODO bacha na indexy nenajdene
            d("Zomato", searchObject.toString())
            d("Zomato 1st restaurant", searchObject.restaurants[0].restaurant.name)

            var sb = StringBuilder()
            for(item in searchObject.restaurants){
                sb.append(item.restaurant.name)
                sb.append("\n")
            }
            tvResponse.text = sb.toString()


            //  TODO    ak je jedno denne menu tak je hned daily menu a inak su daily menus...
            /*
            val dailymenus = mApiService.getDailyMenus(restaurantId2)
            val response2 = dailymenus.await()
            d("Zomato", "------------------------------")
            d("Zomato", response2.toString())
            d("Zomato Menu", response2.dailyMenus[0].dailyMenu.dishes[0].dish.name)
            */
        }

    }
}
