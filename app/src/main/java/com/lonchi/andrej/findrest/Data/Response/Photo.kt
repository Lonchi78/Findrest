package com.lonchi.andrej.findrest.Data.Response

import com.google.gson.annotations.SerializedName

data class Photo(
    val caption: String,
    @SerializedName("comments_count")
    val commentsCount: String,
    @SerializedName("friendly_time")
    val friendlyTime: String,
    val height: String,
    val id: String,
    @SerializedName("likes_count")
    val likesCount: String,
    @SerializedName("res_id")
    val resId: String,
    @SerializedName("thumb_url")
    val thumbUrl: String,
    val timestamp: String,
    val url: String,
    val user: User,
    val width: String
)