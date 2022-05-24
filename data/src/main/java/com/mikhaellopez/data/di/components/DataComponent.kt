package com.mikhaellopez.data.di.components

import android.content.Context
import com.mikhaellopez.data.di.modules.NetModule
import com.mikhaellopez.data.di.modules.PersistenceModule
import com.mikhaellopez.data.di.modules.RepositoryModule
import com.mikhaellopez.domain.repository.RepoRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
@Singleton
@Component(modules = [(NetModule::class), (PersistenceModule::class), (RepositoryModule::class)])
interface DataComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    // Exposed to sub-graphs
    fun provideRepoRepository(): RepoRepository

}