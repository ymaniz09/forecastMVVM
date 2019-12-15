package com.github.ymaniz09.forecastmvvm.data.db.network

import androidx.lifecycle.LiveData
import com.github.ymaniz09.forecastmvvm.data.db.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    
    suspend fun fetchCurrentWeather(
        location: String,
        unitSystem: String
    )
}