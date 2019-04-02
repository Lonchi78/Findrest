package com.lonchi.andrej.findrest.Data.db.entity

import com.google.gson.annotations.SerializedName

data class UserRating(
    @SerializedName("aggregate_rating")
    val aggregateRating: String,
    val votes: String
)