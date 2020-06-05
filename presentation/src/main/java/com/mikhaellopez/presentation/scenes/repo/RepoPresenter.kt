package com.mikhaellopez.presentation.scenes.repo

import com.mikhaellopez.data.extensions.shareReplay
import com.mikhaellopez.data.extensions.startWithSingle
import com.mikhaellopez.domain.functions.DelayFunction
import com.mikhaellopez.domain.usecases.GetRepo
import com.mikhaellopez.domain.usecases.RefreshRepo
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.presentation.scenes.base.presenter.APresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class RepoPresenter
@Inject constructor(
    private val getRepo: GetRepo,
    private val refreshRepo: RefreshRepo,
    private val router: RepoRouter,
    private val scheduler: Scheduler,
    errorMessageFactory: ErrorMessageFactory
) : APresenter<RepoView, RepoViewModel>(errorMessageFactory) {

    override fun attach(view: RepoView) {
        val loadRepo = view.intentLoadData().flatMap { loadRepo(it) }.shareReplay()
        val refreshRepo = view.intentRefreshData().flatMap { refreshData(it) }
        val retryRepo = view.intentErrorRetry().flatMap { retryRepo(it) }

        subscribeViewModel(view, loadRepo, refreshRepo, retryRepo)

        loadRepo.filter { it.data != null }.map { it.data }
            .switchMap { repo -> view.intentActionLink().map { repo!!.url } }
            .subscribe { router.routeToLink(it) }
            .addTo(composite)
    }

    //region USE CASES TO VIEW MODEL
    private fun loadRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo(getRepoParam)
            .startWithSingle(RepoViewModel.createLoading())
            .onErrorReturn { onError(it) }

    private fun refreshData(getRepoParam: RefreshRepo.Param): Observable<RepoViewModel> =
        refreshRepo.execute(getRepoParam).toObservable()
            .map { RepoViewModel.createData(it) }
            .onErrorReturn { RepoViewModel.createSnack(getErrorMessage(it)) }

    private fun retryRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo(getRepoParam)
            .startWithSingle(RepoViewModel.createRetryLoading())
            .onErrorResumeNext(DelayFunction<RepoViewModel>(scheduler))
            .onErrorReturn { onError(it) }

    private fun getRepo(getRepoParam: GetRepo.Param): Observable<RepoViewModel> =
        getRepo.execute(getRepoParam).toObservable()
            .map { RepoViewModel.createData(it) }
    //endregion

    private fun onError(error: Throwable): RepoViewModel =
        RepoViewModel.createError(getErrorMessage(error))

}
