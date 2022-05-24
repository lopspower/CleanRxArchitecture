package com.mikhaellopez.presentation.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.mikhaellopez.presentation.di.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Dagger module that provides context.
 */
@Module
class ApplicationModule {

    @Provides
    @PerApplication
    internal fun provideContext(application: Application): Context = application.baseContext

    @Provides
    @PerApplication
    internal fun providesSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}
