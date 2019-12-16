package com.github.ymaniz09.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.github.ymaniz09.forecastmvvm.data.repository.ForecastRepository
import com.github.ymaniz09.forecastmvvm.internal.UnitSystem
import com.github.ymaniz09.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC // getting from settings later

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
