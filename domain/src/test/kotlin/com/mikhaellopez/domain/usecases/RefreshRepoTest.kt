package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.repository.RepoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RefreshRepoTest {

    @Mock
    private lateinit var repository: RepoRepository

    // Use Case
    private val useCase by lazy { RefreshRepo(repository) }

    @Test
    fun buildUseCase() {
        val repoId = 1L
        val repoName = "repoName"
        val userName = "userName"
        val repo = Repo(
            repoId,
            repoName,
            "description",
            "http://myrepo.com",
            false,
            userName
        )

        whenever(repository.isConnected).thenReturn(true)
        whenever(repository.getRepo(repoName, userName)).thenReturn(Single.just(repo))
        whenever(repository.saveRepo(repo)).thenReturn(Completable.complete())

        useCase.execute(RefreshRepo.Param(repoId, repoName, userName)).test()
            .assertValueCount(1)
            .assertResult(repo)
    }

    @Test
    fun buildUseCaseWithoutNetworkConnection() {
        whenever(repository.isConnected).thenReturn(false)

        useCase.execute(mock()).test()
            .assertError(NoConnectedException::class.java)
    }

}
