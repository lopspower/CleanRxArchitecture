package com.mikhaellopez.domain.repository

import com.mikhaellopez.domain.model.Repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

interface RepoRepository {

    val isConnected: Boolean

    //region LIST REPO
    fun getListRepo(userName: String): Single<List<Repo>>

    fun getCacheListRepo(userName: String): Single<List<Repo>>

    fun sortListRepo(list: List<Repo>): Single<List<Repo>>

    fun saveListRepo(repoList: List<Repo>): Completable
    //endregion

    //region REPO
    fun getRepo(name: String, userName: String): Single<Repo>

    fun getCacheRepo(id: Long): Maybe<Repo>

    fun saveRepo(repo: Repo): Completable

    fun favoriteRepo(repo: Repo): Completable
    //endregion

}
