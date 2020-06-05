package com.mikhaellopez.data.persistence.processor

import com.mikhaellopez.data.persistence.dao.RepoDao
import com.mikhaellopez.data.persistence.entity.RepoEntity
import com.mikhaellopez.data.persistence.processor.base.BaseProcessor
import com.mikhaellopez.domain.functions.CheckPersistenceResultFunction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoProcessor
@Inject internal constructor(private val dao: RepoDao) : BaseProcessor<RepoEntity>(dao) {

    fun get(id: Long): Maybe<RepoEntity> =
        Maybe.fromCallable { dao.get(id) }

    fun getAll(userName: String): Single<List<RepoEntity>> =
        Single.fromCallable { dao.getAll(userName) }

    fun updateIsFavorite(id: Long, isFavorite: Boolean): Completable =
        Single.fromCallable { dao.updateIsFavorite(id, isFavorite) == 1 }
            .flatMapCompletable(CheckPersistenceResultFunction())

}
