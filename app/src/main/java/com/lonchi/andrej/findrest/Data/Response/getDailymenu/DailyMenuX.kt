package com.lonchi.andrej.findrest.Data.Response.getDailymenu

import com.google.gson.annotations.SerializedName

data class DailyMenuX(
    @SerializedName("daily_menu_id")
    val dailyMenuId: String,
    val dishes: List<Dishe>,
    @SerializedName("end_date")
    val endDate: String,
    val name: String,
    @SerializedName("start_date")
    val startDate: String
)