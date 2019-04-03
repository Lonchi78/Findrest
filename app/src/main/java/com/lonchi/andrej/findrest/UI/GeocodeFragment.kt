package com.lonchi.andrej.findrest.UI


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.lonchi.andrej.findrest.BuildConfig
import com.lonchi.andrej.findrest.Data.RestaurantListAdapter
import com.lonchi.andrej.findrest.Data.ZomatoApiService
import com.lonchi.andrej.findrest.Data.db.RestaurantDatabase
import com.lonchi.andrej.findrest.R
import kotlinx.android.synthetic.main.fragment_geocode.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class geocodeFragment : Fragment() {

    //  TODO    ->  reqiure permissions and check GPS and Internet signal

    //  Provides the entry point to the Fused Location Provider API
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    //  Geographical location
    protected var mLastLocation: Location? = null

    //  Geographical data
    var locationLat: Double = -1.0
    var locationLon: Double = -1.0

    //  View of this UI
    private lateinit var viewOfLayout: View

    //  ViewModel for this UI
    private lateinit var searchViewModel: GeocodeViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_geocode, container, false)

        //  Setup fucsed location client
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        //  Progressbar
        val mProgressBar = viewOfLayout.findViewById<ProgressBar>(R.id.pbLoading)
        mProgressBar.visibility = View.VISIBLE

        //  Recycler view adapter
        val recyclerView = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RestaurantListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //  Apply viewModel
        searchViewModel = ViewModelProviders.of(this).get(GeocodeViewmodel::class.java)

        //  Observer
        searchViewModel.foundRestaurants.observe(this, Observer { restaurants ->
            Log.d("Geocode Fragment", "observed")

            // Update the cached copy of the words in the adapter.
            restaurants?.let {
                Log.d("Geocode Fragment", "observed update")

                //  Update recyclerAdapter and stop progressBar
                mProgressBar.visibility = View.INVISIBLE
                adapter.setWords(it)
            }
        })

        return viewOfLayout
    }



    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    /**
     * Shows a [].
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * *
     * @param actionStringId   The text of the action item.
     * *
     * @param listener         The listener associated with the Snackbar action.
     */
    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {

        Log.d(TAG, "SNACKBAR" + getString(mainTextStringId))
    }

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    Log.d(TAG, "getLastLocation:setText")
                    locationLat = mLastLocation!!.latitude
                    locationLon = mLastLocation!!.longitude
                    //getRestaurants()
                    //  Start search
                    searchViewModel.executeGeocode(locationLat, locationLon)

                } else {
                    Log.d(TAG, "getLastLocation:exception", task.exception)
                }
            }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION)

        /*
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        */
        if (shouldProvideRationale) {
            Log.d(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(
                R.string.btn_search, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.d(TAG, "Requesting permission")
            /*
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            */
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                /*
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                */
                Log.d(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                /*
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                */
                showSnackbar(
                    R.string.btn_geocode, R.string.btn_geocode,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

}