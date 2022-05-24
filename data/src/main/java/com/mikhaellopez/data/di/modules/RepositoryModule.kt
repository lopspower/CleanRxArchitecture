package com.mikhaellopez.data.di.modules

import com.mikhaellopez.data.di.providers.NetworkChecker
import com.mikhaellopez.data.extensions.api
import com.mikhaellopez.data.mapper.RepoMapper
import com.mikhaellopez.data.persistence.processor.RepoProcessor
import com.mikhaellopez.data.repository.RepoDataRepository
import com.mikhaellopez.domain.repository.RepoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Dagger module that provides Repository class.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepoDataRepository(
        retrofit: Retrofit,
        repoMapper: RepoMapper,
        repoProcessor: RepoProcessor,
        networkChecker: NetworkChecker
    ): RepoRepository =
        RepoDataRepository(retrofit.api(), repoMapper, repoProcessor, networkChecker)

}
