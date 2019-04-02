package com.lonchi.andrej.findrest.Data.db.entity

import com.google.gson.annotations.SerializedName

data class FetchSearch(
    @SerializedName("restaurants")
    val restaurants: List<Restaurants>
)