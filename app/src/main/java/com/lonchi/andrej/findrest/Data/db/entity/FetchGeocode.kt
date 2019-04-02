package com.lonchi.andrej.findrest.Data.db.entity

import com.google.gson.annotations.SerializedName

data class FetchGeocode(
    @SerializedName("nearby_restaurants")
    val nearbyRestaurants: List<NearbyRestaurant>
)