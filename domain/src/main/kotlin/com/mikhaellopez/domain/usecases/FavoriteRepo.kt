package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.mikhaellopez.domain.usecases.base.CompletableUseCase
import com.mikhaellopez.domain.usecases.base.Logger
import com.mikhaellopez.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * This class is an implementation of [CompletableUseCase] that represents a use case
 * to add to favorite a [Repo].
 */
class FavoriteRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : CompletableUseCase<Repo>(useCaseScheduler, logger) {

    override fun build(param: Repo): Completable =
        repoRepository.favoriteRepo(param)

}
