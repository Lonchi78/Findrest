package com.lonchi.andrej.findrest.Data.Response.getDailymenu

import com.google.gson.annotations.SerializedName

data class Dish(
    @SerializedName("dish_id")
    val dishId: String,
    val name: String,
    val price: String
)