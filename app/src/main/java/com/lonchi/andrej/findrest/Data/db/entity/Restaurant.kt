package com.lonchi.andrej.findrest.Data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurant")
data class Restaurant(
    @PrimaryKey
    val id: String,
    val name: String,
    val cuisines: String,
    @Embedded(prefix = "location_")
    val location: Location,
    val thumb: String,
    val url: String,
    @SerializedName("menu_url")
    val menuUrl: String,
    @SerializedName("photos_url")
    val photosUrl: String,
    @Embedded(prefix = "user_rating_")
    @SerializedName("user_rating")
    val userRating: UserRating
)