package com.mikhaellopez.presentation.scenes.repo

import com.mikhaellopez.domain.exception.NoConnectedException
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.usecases.GetRepo
import com.mikhaellopez.domain.usecases.RefreshRepo
import com.mikhaellopez.presentation.exception.ErrorMessageFactoryTest
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class RepoPresenterTest {

    // View
    private lateinit var view: RepoView

    // Presenter
    private val getRepo = mock<GetRepo>()
    private val refreshRepo = mock<RefreshRepo>()
    private val router = mock<RepoRouter>()
    private val errorMessageFactoryTest = ErrorMessageFactoryTest(mock())
    private val presenter by lazy {
        RepoPresenter(getRepo, refreshRepo, router, TestScheduler(), errorMessageFactoryTest)
    }

    // Properties
    private val repoId = 1L
    private val repoName = "RxAnimation"
    private val userName = "lopspower"

    // Intents
    private val intentActionLink = PublishSubject.create<Unit>()

    @Before
    fun setup() {
        view = mock {
            on { intentLoadData() } doReturn Observable.never()
            on { intentRefreshData() } doReturn Observable.never()
            on { intentErrorRetry() } doReturn Observable.never()
            on { intentActionLink() } doReturn intentActionLink
        }
    }

    @Test
    fun loadData() {
        val param = GetRepo.Param(repoId, repoName, userName)
        val repo = mock<Repo>()

        whenever(getRepo.execute(param)).thenReturn(Single.just(repo))
        whenever(view.intentLoadData()).thenReturn(Observable.just(param))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoViewModel.createLoading())
        verify(view, times(1)).render(RepoViewModel.createData(repo))

        // Detach
        presenter.detach()
    }

    @Test
    fun loadDataWhenUseCaseReturnException() {
        val param = GetRepo.Param(repoId, repoName, userName)
        val noConnectedException = NoConnectedException

        whenever(getRepo.execute(param)).thenReturn(Single.error(noConnectedException))
        whenever(view.intentLoadData()).thenReturn(Observable.just(param))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoViewModel.createLoading())
        verify(view, times(1)).render(
            RepoViewModel.createError(
                errorMessageFactoryTest.getError(
                    noConnectedException
                )
            )
        )

        // Detach
        presenter.detach()
    }

    @Test
    fun refresh() {
        val param = RefreshRepo.Param(repoId, repoName, userName)
        val repo = mock<Repo>()

        whenever(refreshRepo.execute(param)).thenReturn(Single.just(repo))
        whenever(view.intentRefreshData()).thenReturn(Observable.just(param))

        // Attach
        presenter.attach(view)

        verify(view, times(1)).render(any())
        verify(view, times(1)).render(RepoViewModel.createData(repo))

        // Detach
        presenter.detach()
    }

    @Test
    fun retry() {
        val param = GetRepo.Param(repoId, repoName, userName)
        val repo = mock<Repo>()

        whenever(getRepo.execute(param)).thenReturn(Single.just(repo))
        whenever(view.intentErrorRetry()).thenReturn(Observable.just(param))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoViewModel.createRetryLoading())
        verify(view, times(1)).render(RepoViewModel.createData(repo))


        // Detach
        presenter.detach()
    }

    @Test
    fun openData() {
        val param = GetRepo.Param(repoId, repoName, userName)
        val repo = Repo(repoId, repoName, "description", "http://repo.com", false, userName)

        whenever(getRepo.execute(param)).thenReturn(Single.just(repo))
        whenever(view.intentLoadData()).thenReturn(Observable.just(param))

        // Attach
        presenter.attach(view)

        verify(view, times(2)).render(any())
        verify(view, times(1)).render(RepoViewModel.createLoading())
        verify(view, times(1)).render(RepoViewModel.createData(repo))

        intentActionLink.onNext(Unit)

        verify(router, times(1)).routeToLink(repo.url)

        // Detach
        presenter.detach()
    }

}
