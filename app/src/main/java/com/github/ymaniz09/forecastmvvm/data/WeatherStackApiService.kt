package com.github.ymaniz09.forecastmvvm.data

import com.github.ymaniz09.forecastmvvm.data.db.network.ConnectivityInterceptor
import com.github.ymaniz09.forecastmvvm.data.db.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "0cc452ea9abb4b46dc166932f38bd9df"
const val BASE_URL = "http://api.weatherstack.com/"


interface WeatherStackApiService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("query") location: String,
        @Query("units") unitSystem: String = "m"
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherStackApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherStackApiService::class.java)
        }
    }
}