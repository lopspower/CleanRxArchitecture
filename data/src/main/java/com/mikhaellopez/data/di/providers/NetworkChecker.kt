package com.mikhaellopez.data.di.providers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Copyright (C) 2020 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
class NetworkChecker(private val context: Context) {

    @Suppress("DEPRECATION")
    val isConnected: Boolean
        get() = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                } ?: false
            } else {
                activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                } ?: false
            }
        } ?: false

}
