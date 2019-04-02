package com.lonchi.andrej.findrest.Data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.lonchi.andrej.findrest.Data.db.entity.Restaurant
import com.lonchi.andrej.findrest.R


class RestaurantListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<RestaurantListAdapter.WordViewHolder>() {

    private lateinit var mContext: Context
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var restaurants = emptyList<Restaurant>()

    init {
        mContext = context
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantItemView: ConstraintLayout = itemView.findViewById(R.id.cardView)
        val restaurantMenu: ImageView = itemView.findViewById(R.id.icRestaurantMenu)
        val restaurantThumb: ImageView = itemView.findViewById(R.id.ivRestaurantThumb)
        val restaurantName: TextView = itemView.findViewById(R.id.tvRestaurantName)
        val restaurantAddress: TextView = itemView.findViewById(R.id.tvRestaurantAddress)
        val restaurantRating: TextView = itemView.findViewById(R.id.tvRestaurantRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = restaurants[position]
        holder.restaurantName.text = current.name.toString()
        holder.restaurantAddress.text = current.location.address.toString()
        holder.restaurantRating.text = current.userRating.aggregateRating.toString()

        //  Load thumb from url
        Glide.with(mContext)
            .load(current.thumb)
            .apply(RequestOptions().override(100, 100).error(R.color.scheme1Yellow).fitCenter())
            .into(holder.restaurantThumb);

        //  Click listener on itemView -> restaurant detail fragment
        holder.restaurantItemView.setOnClickListener {
            Log.d("Zomato","onViewClick")
        }

        //  Click listener on menu -> dailymenu fragment
        holder.restaurantMenu.setOnClickListener {
            Log.d("Zomato","onMenuClick")
        }
    }

    internal fun setWords(words: List<Restaurant>) {
        this.restaurants = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = restaurants.size
}