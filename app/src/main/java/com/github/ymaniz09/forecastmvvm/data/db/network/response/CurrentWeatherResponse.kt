package com.github.ymaniz09.forecastmvvm.data.db.network.response

import com.github.ymaniz09.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.github.ymaniz09.forecastmvvm.data.db.entity.Location
import com.github.ymaniz09.forecastmvvm.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)