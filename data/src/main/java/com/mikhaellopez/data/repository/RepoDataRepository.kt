package com.mikhaellopez.data.repository

import com.mikhaellopez.data.di.providers.NetworkChecker
import com.mikhaellopez.data.mapper.RepoMapper
import com.mikhaellopez.data.net.api.GitHubApi
import com.mikhaellopez.data.persistence.processor.RepoProcessor
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.toObservable

/**
 * [RepoRepository] for retrieving repo data.
 */
class RepoDataRepository(
    private val gitHubApi: GitHubApi,
    private val repoMapper: RepoMapper,
    private val repoProcessor: RepoProcessor,
    private val networkChecker: NetworkChecker
) : RepoRepository {

    override val isConnected: Boolean
        get() = networkChecker.isConnected

    //region LIST REPO
    override fun getListRepo(userName: String): Single<List<Repo>> =
        gitHubApi.getListRepos(userName)
            .map { repoMapper.transform(it, userName) }

    override fun getCacheListRepo(userName: String): Single<List<Repo>> =
        repoProcessor.getAll(userName)
            .map { repoMapper.transform(it) }

    override fun saveListRepo(repoList: List<Repo>): Completable =
        repoList.toObservable().flatMapCompletable { saveRepo(it) }

    override fun sortListRepo(list: List<Repo>): Single<List<Repo>> =
        Single.just(list.sortedWith(Comparator { o1, o2 ->
            when {
                o1.id < o2.id -> 1
                o1.id == o2.id -> 0
                else -> -1
            }
        }))
    //endregion

    //region REPO
    override fun getRepo(name: String, userName: String): Single<Repo> =
        gitHubApi.getRepo(userName, name)
            .map { repoMapper.transform(it, userName) }

    override fun getCacheRepo(id: Long): Maybe<Repo> =
        repoProcessor.get(id)
            .map { repoMapper.transform(it) }

    override fun saveRepo(repo: Repo): Completable =
        repoProcessor.get(repo.id)
            .defaultIfEmpty(repoMapper.transformToEntity(repo))
            .flatMapCompletable {
                repoProcessor.insert(
                    repoMapper.transformToEntity(
                        repo.copy(
                            isFavorite = it.isFavorite
                        )
                    )
                )
            }

    override fun favoriteRepo(repo: Repo): Completable =
        repoProcessor.updateIsFavorite(repo.id, !repo.isFavorite)
    //endregion

}
