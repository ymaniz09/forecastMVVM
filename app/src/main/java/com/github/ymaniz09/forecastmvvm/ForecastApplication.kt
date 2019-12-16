package com.github.ymaniz09.forecastmvvm

import android.app.Application
import com.github.ymaniz09.forecastmvvm.data.WeatherStackApiService
import com.github.ymaniz09.forecastmvvm.data.db.ForecastDatabase
import com.github.ymaniz09.forecastmvvm.data.db.network.ConnectivityInterceptor
import com.github.ymaniz09.forecastmvvm.data.db.network.ConnectivityInterceptorImpl
import com.github.ymaniz09.forecastmvvm.data.db.network.WeatherNetworkDataSource
import com.github.ymaniz09.forecastmvvm.data.db.network.WeatherNetworkDataSourceImpl
import com.github.ymaniz09.forecastmvvm.data.repository.ForecastRepository
import com.github.ymaniz09.forecastmvvm.data.repository.ForecastRepositoryImpl
import com.github.ymaniz09.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherStackApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}