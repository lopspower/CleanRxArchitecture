package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.mikhaellopez.domain.usecases.base.Logger
import com.mikhaellopez.domain.usecases.base.SingleUseCase
import com.mikhaellopez.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a [Repo].
 */
class GetCacheRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<Repo, Long>(useCaseScheduler, logger) {

    override fun build(param: Long): Single<Repo> =
        repoRepository.getCacheRepo(param)
            .switchIfEmpty(Maybe.error(PersistenceException))
            .toSingle()

}
