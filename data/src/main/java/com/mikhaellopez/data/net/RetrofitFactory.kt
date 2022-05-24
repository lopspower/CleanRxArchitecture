package com.mikhaellopez.data.net

import android.content.Context

import com.google.gson.Gson

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * RetrofitFactory to generate a Retrofit instance.
 * It sets up request logging and a Gson type adapter.
 */
object RetrofitFactory {

    // Base URL: always ends with /
    private const val URL_MAIN_WEBSERVICE = "https://api.github.com/"

    /**
     * Get [Retrofit] instance.
     * @return instances of [Retrofit]
     */
    fun getRetrofit(
        context: Context,
        gson: Gson,
        okHttpClientFactory: OkHttpClientFactory
    ): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL_MAIN_WEBSERVICE)
            .client(okHttpClientFactory.createOkHttpClient(context))
            .build()

}
