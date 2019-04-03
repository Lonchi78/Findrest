package com.lonchi.andrej.findrest.UI


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.findrest.Data.RestaurantListAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import com.lonchi.andrej.findrest.Data.ZomatoApiService
import com.lonchi.andrej.findrest.R
import kotlinx.coroutines.*
import java.lang.StringBuilder


class SearchFragment : Fragment() {

    //  View of this layout
    private lateinit var viewOfLayout: View

    //  ViewModel for this UI
    private lateinit var searchViewModel: SearchViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_search, container, false)

        //  Progressbar
        val mProgressBar = viewOfLayout.findViewById<ProgressBar>(R.id.pbLoading)
        mProgressBar.visibility = View.VISIBLE

        //  Get passed argument
        val argumentQuery = arguments?.getString("searchQuery")

        //  Recycler view adapter
        val recyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RestaurantListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //  Apply viewModel
        searchViewModel = ViewModelProviders.of(this).get(SearchViewmodel::class.java)

        //  Observer
        searchViewModel.foundRestaurants.observe(this, Observer { restaurants ->
            Log.d("FUCK", "UI upd")
            //Log.d("FUCK", restaurants[0].name)
            Log.d("FUCK", restaurants?.toString())
            // Update the cached copy of the words in the adapter.
            restaurants?.let {
                Log.d("FUCK", "UI update")
                mProgressBar.visibility = View.INVISIBLE
                adapter.setWords(it)
            }
        })

        //  Start search
        searchViewModel.executeSearch(argumentQuery.toString())
        Log.d("FUCK", "UI call exec")

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
