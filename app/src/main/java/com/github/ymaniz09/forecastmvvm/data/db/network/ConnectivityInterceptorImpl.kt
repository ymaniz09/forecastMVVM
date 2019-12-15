package com.github.ymaniz09.forecastmvvm.data.db.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.github.ymaniz09.forecastmvvm.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) {
            throw NoConnectivityException()
        }

        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )

        return networkCapabilities?.let {
            return it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        } ?: false
    }
}