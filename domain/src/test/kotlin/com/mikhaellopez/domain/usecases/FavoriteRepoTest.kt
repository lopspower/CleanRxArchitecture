package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Properties
    private val repoId: Long = 1
    private val repo =
        Repo(
            repoId,
            "repoName",
            "description",
            "http://myrepo.com",
            false,
            "userName"
        )

    // Use Case
    private val useCase by lazy { FavoriteRepo(repository) }

    @Test
    fun buildUseCase() {
        whenever(repository.favoriteRepo(repo)).thenReturn(Completable.complete())

        useCase.execute(repo).test()
            .assertResult()
    }

    @Test
    fun buildUseCaseWithPersistenceException() {
        whenever(repository.favoriteRepo(repo)).thenReturn(Completable.error(PersistenceException))

        useCase.execute(repo).test()
            .assertError(PersistenceException::class.java)
    }

}
