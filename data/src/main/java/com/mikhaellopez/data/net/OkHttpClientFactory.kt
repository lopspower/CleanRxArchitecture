package com.mikhaellopez.data.net

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mikhaellopez.data.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Copyright (C) 2021 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
open class OkHttpClientFactory {

    open fun createOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    enableDebugTools(context)
                }
                updateTimeout()
            }
            .build()


    private fun OkHttpClient.Builder.enableDebugTools(context: Context) {
        addInterceptor(StethoInterceptor())
        addInterceptor(ChuckInterceptor(context))
    }

    private fun OkHttpClient.Builder.updateTimeout(read: Long = 60, write: Long = 60) {
        readTimeout(read, TimeUnit.SECONDS)
        writeTimeout(write, TimeUnit.SECONDS)
    }

}