package com.mikhaellopez.presentation.scenes.repolist

import com.mikhaellopez.data.extensions.startWithSingle
import com.mikhaellopez.domain.functions.DelayFunction
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.domain.usecases.FavoriteRepo
import com.mikhaellopez.domain.usecases.GetCacheRepo
import com.mikhaellopez.domain.usecases.GetListRepo
import com.mikhaellopez.domain.usecases.RefreshListRepo
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.presentation.scenes.base.presenter.APresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoListPresenter
@Inject constructor(
    private val getListRepo: GetListRepo,
    private val refreshListRepo: RefreshListRepo,
    private val favoriteRepo: FavoriteRepo,
    private val getCacheRepo: GetCacheRepo,
    private val router: RepoListRouter,
    private val scheduler: Scheduler,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoListView, RepoListViewModel>(errorMessageFactory) {

    override fun attach(view: RepoListView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo(it) }
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }
        val favoriteRepo =
            view.intentFavorite().flatMap { (position, repo) -> favoriteRepo(position, repo) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo, favoriteRepo)

        view.openRepo()
            .subscribe { (repo, userName) -> router.routeToRepo(repo.id, repo.name, userName) }
            .addTo(composite)
    }

    //region USE CASES TO VIEW MODEL
    private fun loadRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .startWithSingle(RepoListViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshData(userName: String): Observable<RepoListViewModel> =
        refreshListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .onErrorReturn { RepoListViewModel.createSnack(getErrorMessage(it)) }

    private fun retryRepo(userName: String): Observable<RepoListViewModel> =
        getListRepo.execute(userName).toObservable()
            .map { RepoListViewModel.createData(it) }
            .startWithSingle(RepoListViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction(scheduler))
            .onErrorReturn { onError(it) }

    private fun favoriteRepo(position: Int, repo: Repo): Observable<RepoListViewModel> =
        favoriteRepo.execute(repo).toSingleDefault(Unit).toObservable()
            .flatMap { getCacheRepo.execute(repo.id).toObservable() }
            .map { RepoListViewModel.createFavoriteRepo(position, it) }
            .onErrorReturn { onError(it) }
    //endregion

    private fun onError(error: Throwable): RepoListViewModel =
        RepoListViewModel.createError(getErrorMessage(error))

}
