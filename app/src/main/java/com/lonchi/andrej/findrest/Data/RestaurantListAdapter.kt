package com.lonchi.andrej.findrest.Data

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import com.lonchi.andrej.findrest.Data.db.entity.Restaurants
import com.lonchi.andrej.findrest.R


class RestaurantListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RestaurantListAdapter.WordViewHolder>() {

    //  VGlobal variables
    private var mContext: Context
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var restaurants = emptyList<Restaurant>()

    init {
        //  Store context
        mContext = context
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  Get views from itemLayout
        val restaurantItemView: ConstraintLayout = itemView.findViewById(R.id.cardView)
        val restaurantMenu: ImageView = itemView.findViewById(R.id.icRestaurantMenu)
        val restaurantThumb: ImageView = itemView.findViewById(R.id.ivRestaurantThumb)
        val restaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val restaurantAddress: TextView = itemView.findViewById(R.id.tvRestaurantAddress)
        val restaurantRating: TextView = itemView.findViewById(R.id.tvRestaurantRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        //  Inflate item layout
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        //  Get current object
        val current = restaurants[position]

        //  Set itemView to object data
        holder.restaurantName.text = current.name.toString()
        holder.restaurantAddress.text = current.location.address.toString()
        holder.restaurantRating.text = current.userRating.aggregateRating.toString()

        //  Load thumb from url via Glide
        Glide.with(mContext)
            .load(current.thumb)
            .apply(RequestOptions().override(100, 100).error(R.drawable.ic_not_interested_black_24dp).dontTransform().placeholder(R.drawable.ic_file_download_black_24dp).diskCacheStrategy(
                DiskCacheStrategy.ALL))
            .into(holder.restaurantThumb)

        //  Click listener on itemView -> restaurant detail fragment
        holder.restaurantItemView.setOnClickListener {
            Log.d("RecyclerView","onViewClick")

            //  Open Restaurant fragment
            val restaurantIdBundle = Bundle()
            restaurantIdBundle.putString("res_id", current.id)
            it.findNavController().navigate(R.id.toRestaurant, restaurantIdBundle)
        }

        //  Click listener on menu -> dailymenu fragment
        holder.restaurantMenu.setOnClickListener {
            Log.d("RecyclerView","onMenuClick")

            //  Open Dailymenu fragment
            val menuUrlBundle = Bundle()
            menuUrlBundle.putString("menu_url", current.menuUrl)
            it.findNavController().navigate(R.id.toDailymenu, menuUrlBundle)
        }
    }

    internal fun setWords(words: List<Restaurant>) {
        //  Rebuild recyclerView
        this.restaurants = words
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): Restaurant {
        //  Return item at position
        return restaurants[position]
    }

    override fun getItemCount() = restaurants.size
}