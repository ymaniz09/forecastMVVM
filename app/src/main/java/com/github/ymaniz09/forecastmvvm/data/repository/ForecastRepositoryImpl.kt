package com.github.ymaniz09.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.github.ymaniz09.forecastmvvm.data.db.CurrentWeatherDao
import com.github.ymaniz09.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.github.ymaniz09.forecastmvvm.data.db.network.WeatherNetworkDataSource
import com.github.ymaniz09.forecastmvvm.data.db.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {

            return@withContext currentWeatherDao.getWeatherEntry()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherDAta() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles",
            "m"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}