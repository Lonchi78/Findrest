package com.lonchi.andrej.findrest.Data.Response

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("all_reviews")
    val allReviews: List<AllReview>,
    @SerializedName("all_reviews_count")
    val allReviewsCount: String,
    @SerializedName("average_cost_for_two")
    val averageCostForTwo: String,
    val cuisines: String,
    val currency: String,
    val deeplink: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("featured_image")
    val featuredImage: String,
    @SerializedName("has_online_delivery")
    val hasOnlineDelivery: String,
    @SerializedName("has_table_booking")
    val hasTableBooking: String,
    val id: String,
    @SerializedName("is_delivering_now")
    val isDeliveringNow: String,
    val location: Location,
    @SerializedName("menu_url")
    val menuUrl: String,
    val name: String,
    @SerializedName("phone_numbers")
    val phoneNumbers: String,
    @SerializedName("photo_count")
    val photoCount: String,
    val photos: List<Photo>,
    @SerializedName("photos_url")
    val photosUrl: String,
    @SerializedName("price_range")
    val priceRange: String,
    val thumb: String,
    val url: String,
    @SerializedName("user_rating")
    val userRating: UserRating
)