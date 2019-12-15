package com.github.ymaniz09.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.github.ymaniz09.forecastmvvm.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry>
}