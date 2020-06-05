package com.mikhaellopez.data.extensions

import retrofit2.Retrofit

inline fun <reified T> Retrofit.api(): T = this.create(T::class.java)