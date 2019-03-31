package com.lonchi.andrej.findrest.Data.Response

import com.google.gson.annotations.SerializedName

data class Location(
    val address: String,
    val city: String,
    @SerializedName("country_id")
    val countryId: String,
    val latitude: String,
    val locality: String,
    val longitude: String,
    val zipcode: String
)