package com.lonchi.andrej.findrest.Data.Response

import com.google.gson.annotations.SerializedName

data class AllReview(
    @SerializedName("comments_count")
    val commentsCount: String,
    val id: String,
    val likes: String,
    val rating: String,
    @SerializedName("rating_color")
    val ratingColor: String,
    @SerializedName("rating_text")
    val ratingText: String,
    @SerializedName("review_text")
    val reviewText: String,
    @SerializedName("review_time_friendly")
    val reviewTimeFriendly: String,
    val timestamp: String,
    val user: User
)