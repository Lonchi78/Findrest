package com.lonchi.andrej.findrest.UI


import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lonchi.andrej.findrest.R
import kotlinx.android.synthetic.main.fragment_restaurant.*


class RestaurantFragment : Fragment() {

    //  TODO   ->   onClick events for urls ( url, menuUrl, photosUrl )

    //  View of this UI
    private lateinit var viewOfLayout: View

    //  ViewModel for this UI
    private lateinit var restaurantViewModel: RestaurantViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_restaurant, container, false)

        //  Get argument (restaurant id)
        val res_id = arguments?.getString("res_id")
        groupLoading?.visibility = View.INVISIBLE
        tvResName?.visibility = View.INVISIBLE

        //  Apply viewModel
        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewmodel::class.java)
        restaurantViewModel.getRestaurant(res_id ?: "-1")

        //  Progressbar
        val mProgressBar = viewOfLayout.findViewById<ProgressBar>(R.id.pbLoading)
        mProgressBar.visibility = View.VISIBLE

        //  Observer
        restaurantViewModel.oneRestaurant.observe(this, Observer { restaurant ->
            Log.d("Restaurant Fragment", "observed")

            // Update the cached copy of the words in the adapter.
            restaurant?.let {
                Log.d("Search Fragment", "observed update")

                //  Update views and visibilities
                tvResName.visibility = View.VISIBLE
                groupLoading.visibility = View.VISIBLE
                mProgressBar.visibility = View.INVISIBLE
                tvResName.text = restaurant.name
                tvResAddres.text = restaurant.location.address
                tvResCuisines.text = restaurant.cuisines
                tvResPhotos.text = restaurant.photosUrl
                tvResMenu.text = restaurant.menuUrl
                tvResUrl.text = restaurant.url
                tvResRating.text = restaurant.userRating.aggregateRating

                //  Load thumb from url
                Glide.with(requireActivity())
                    .load(restaurant.thumb)
                    .apply(
                        RequestOptions().override(300, 300).error(R.drawable.ic_not_interested_black_24dp).dontTransform().placeholder(R.drawable.ic_file_download_black_24dp).diskCacheStrategy(
                            DiskCacheStrategy.ALL))
                    .into(ivResThumb)
            }
        })

        return viewOfLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
