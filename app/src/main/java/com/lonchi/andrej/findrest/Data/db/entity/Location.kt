package com.lonchi.andrej.findrest.Data.db.entity

import com.google.gson.annotations.SerializedName

data class Location(
    val address: String,
    val city: String,
    val latitude: String,
    val longitude: String
)