package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.functions.ConnectionFilter
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.mikhaellopez.domain.usecases.base.Logger
import com.mikhaellopez.domain.usecases.base.SingleUseCase
import com.mikhaellopez.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all [Repo] but just with webservice (no cache data).
 */
class RefreshListRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Repo>, String>(useCaseScheduler, logger) {

    override fun build(param: String): Single<List<Repo>> =
        Single.just(repoRepository.isConnected)
            .filter(ConnectionFilter())
            .flatMapSingle { _ ->
                repoRepository.getListRepo(param)
                    .flatMap {
                        repoRepository.saveListRepo(it)
                            .andThen(repoRepository.getCacheListRepo(param))
                    }
                    .flatMap { repoRepository.sortListRepo(it) }
            }.toSingle()


}
