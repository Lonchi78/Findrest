package com.lonchi.andrej.findrest.Data.Response

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("aggregate_rating")
    val aggregateRating: String,
    @SerializedName("rating_color")
    val ratingColor: String,
    @SerializedName("rating_text")
    val ratingText: String,
    val votes: String
)