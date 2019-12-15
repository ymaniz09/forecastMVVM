package com.github.ymaniz09.forecastmvvm.data.db.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ymaniz09.forecastmvvm.data.WeatherStackApiService
import com.github.ymaniz09.forecastmvvm.data.db.network.response.CurrentWeatherResponse
import com.github.ymaniz09.forecastmvvm.internal.NoConnectivityException
import com.github.ymaniz09.forecastmvvm.util.TAG

class WeatherNetworkDataSourceImpl(
    private val weatherStackApiService: WeatherStackApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, unitSystem: String) {
        try {
            val fetchCurrentWeather = weatherStackApiService
                .getCurrentWeather(location, unitSystem)

            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e(TAG, "No internet connection.", e)
        }
    }
}