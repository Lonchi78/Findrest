package com.lonchi.andrej.findrest.UI


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lonchi.andrej.findrest.Data.RestaurantListAdapter
import com.lonchi.andrej.findrest.R
import kotlinx.android.synthetic.main.fragment_offline.*


class OfflineFragment : Fragment() {

    //  View of this layout
    private lateinit var viewOfLayout: View

    //  ViewModel for this UI
    private lateinit var offlineViewModel: OfflineViewmodel
    private val TAG = "Offline Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_offline, container, false)

        //  Progressbar
        val mProgressBar = viewOfLayout.findViewById<ProgressBar>(R.id.pbLoading)
        mProgressBar.visibility = View.VISIBLE

        //  Recycler view adapter
        val recyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RestaurantListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //  Apply viewModel
        offlineViewModel = ViewModelProviders.of(this).get(OfflineViewmodel::class.java)

        //  Observer
        offlineViewModel.allRestaurants.observe(this, Observer { restaurants ->
            //  Update the cached copy of the restaurants in the adapter
            restaurants?.let {
                mProgressBar.visibility = View.INVISIBLE
                adapter.setWords(it)
            }
        })

        //  Add swipe gesture to delete item
        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //  Do nothing
                //  TODO    arguments should be empty for onMove func, null alternative?
                Log.d(TAG, "item callback onMove()" )
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //  Swipe gest -> delete restaurant
                Log.d(TAG, "item callback onSwiped() on " )
                val deletedRestaurant = adapter.getItemAt(viewHolder.adapterPosition)
                offlineViewModel.delete( deletedRestaurant )
            }

        }
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)

        //  Command to delete whole database!
        //deleteAllDatabase()

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun deleteAllDatabase(){
        offlineViewModel.deleteAll()
    }
}
