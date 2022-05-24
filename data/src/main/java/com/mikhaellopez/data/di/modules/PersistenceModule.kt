package com.mikhaellopez.data.di.modules

import android.content.Context
import com.mikhaellopez.data.persistence.AppDatabase
import com.mikhaellopez.data.persistence.DatabaseFactory
import com.mikhaellopez.data.persistence.dao.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Dagger module that provides Persistence class.
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
        DatabaseFactory.getDatabase(context)

    @Provides
    @Singleton
    internal fun provideRepoDao(appDatabase: AppDatabase): RepoDao = appDatabase.repoDao()

}
