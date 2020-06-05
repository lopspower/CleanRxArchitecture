package com.mikhaellopez.presentation.scenes.repolist

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.mikhaellopez.data.helper.TimberWrapper
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.scenes.base.view.ABaseDataFragment
import com.mikhaellopez.presentation.scenes.base.view.ContentState
import com.mikhaellopez.presentation.scenes.base.view.LoadingState
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.repo_list_fragment.*
import javax.inject.Inject

class RepoListFragment : ABaseDataFragment(R.layout.repo_list_fragment), RepoListView {

    companion object {
        fun newInstance(): RepoListFragment = RepoListFragment()
    }

    @Inject
    lateinit var presenter: RepoListPresenter

    private fun getParam() = "lopspower"

    private val repoAdapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    private fun initView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = repoAdapter
    }

    //region INTENTS
    override fun intentLoadData(): Observable<String> =
        Observable.just(getParam())

    override fun intentRefreshData(): Observable<String> =
        swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        btnErrorRetry.clicks().map { getParam() }

    override fun intentFavorite(): Observable<Pair<Int, Repo>> =
        repoAdapter.repoFavoriteIntent

    override fun openRepo(): Observable<Pair<Repo, String>> =
        repoAdapter.repoClickIntent.map { it to getParam() }
    //endregion

    //region RENDER
    override fun render(viewModel: RepoListViewModel) {
        TimberWrapper.d { "render: $viewModel" }

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        showRefreshingLoading(swipeRefreshLayout, false)
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        showContent(content, viewModel.contentState == ContentState.CONTENT)
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
        renderFavoriteRepo(viewModel.favoriteRepo, viewModel.favoriteRepoPosition)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repoList: List<Repo>?) {
        repoList?.also {
            repoAdapter.data = it.toMutableList()
            recyclerView.scrollToPosition(0)
        }
    }

    private fun renderFavoriteRepo(favoriteRepo: Repo?, favoriteRepoPosition: Int?) {
        if (favoriteRepo != null && favoriteRepoPosition != null) {
            repoAdapter.setData(favoriteRepoPosition, favoriteRepo)
        }
    }
    //endregion

}
