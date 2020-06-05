package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Properties
    private val repoName = "repoName"
    private val userName = "userName"
    private val repo = Repo(
        1,
        repoName,
        "description",
        "http://myrepo.com",
        false,
        userName
    )
    private val param = GetRepo.Param(repo.id, repo.name, userName)

    // Use Case
    private val useCase by lazy { GetRepo(repository) }

    @Test
    fun buildUseCase() {
        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.just(repo))
        whenever(repository.saveRepo(repo)).thenReturn(Completable.complete())
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.just(repo))

        useCase.execute(param).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnection() {
        whenever(repository.isConnected).thenReturn(false)
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.just(repo))
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.never())

        useCase.execute(param).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnectionAndDataCache() {
        whenever(repository.isConnected).thenReturn(false)
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.empty()) // throw NoConnectedException here
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.never())

        useCase.execute(param).test()
            .assertError(NoConnectedException::class.java)
    }

    @Test
    fun buildUseCaseWithPersistenceException() {
        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.just(repo))
        whenever(repository.saveRepo(repo)).thenReturn(Completable.error(PersistenceException))
        whenever(repository.getCacheRepo(repo.id)).thenReturn(Maybe.empty())

        useCase.execute(param).test()
            .assertError(PersistenceException::class.java)
    }

}
