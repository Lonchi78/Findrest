package com.lonchi.andrej.findrest.UI


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.lonchi.andrej.findrest.Data.RestaurantListAdapter
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.R
import kotlinx.android.synthetic.main.fragment_offline.*


class OfflineFragment : Fragment() {

    private lateinit var viewOfLayout: View

    private lateinit var geocodeViewModel: GeocodeViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_offline, container, false)

        //  Recycler view adapter
        val recyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RestaurantListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        //  Apply viewModel
        geocodeViewModel = ViewModelProviders.of(this).get(GeocodeViewmodel::class.java)

        //  Observer
        geocodeViewModel.allWords.observe(this, Observer { restaurants ->
            // Update the cached copy of the words in the adapter.
            restaurants?.let { adapter.setWords(it) }
        })


        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        //  TODO    Hocus
        val db = Room.databaseBuilder(
            requireContext(),
            RestaurantDatabase::class.java, "restaurant.db"
        ).allowMainThreadQueries().build()

        val restaurants = db.RestaurantDao().getFirstRestaurant()
        tvHocus.text = restaurants.name
        */

    }
}
