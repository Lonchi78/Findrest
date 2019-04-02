package com.lonchi.andrej.findrest.Data.Response.getDailymenu

import com.google.gson.annotations.SerializedName

data class DailyMenu(
    @SerializedName("daily_menu")
    val dailyMenu: DailyMenuX
)