package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RefreshListRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Properties
    private val userName = "userName"

    // Use Case
    private val useCase by lazy { RefreshListRepo(repository) }

    @Test
    fun buildUseCase() {
        val repoList = mock<List<Repo>>()

        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getListRepo(userName)).thenReturn(Single.just(repoList))
        whenever(repository.saveListRepo(repoList)).thenReturn(Completable.complete())
        whenever(repository.getCacheListRepo(userName)).thenReturn(Single.just(repoList))
        whenever(repository.sortListRepo(repoList)).thenReturn(Single.just(repoList))

        useCase.execute(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnection() {
        whenever(repository.isConnected).thenReturn(false)

        useCase.execute(userName).test()
            .assertError(NoConnectedException::class.java)
    }

}
