package com.lonchi.andrej.findrest.Data.Response.getDailymenu

import com.google.gson.annotations.SerializedName
import com.lonchi.andrej.findrest.Data.Response.getDailymenu.DailyMenu

data class DailyMenus(
    @SerializedName("daily_menus")
    val dailyMenus: List<DailyMenu>,
    val status: String
)