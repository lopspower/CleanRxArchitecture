package com.mikhaellopez.presentation.scenes.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import com.mikhaellopez.data.helper.TimberWrapper
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.databinding.RepoListFragmentBinding
import com.mikhaellopez.presentation.scenes.base.view.ABaseDataFragment
import com.mikhaellopez.presentation.scenes.base.view.ContentState
import com.mikhaellopez.presentation.scenes.base.view.LoadingState
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RepoListFragment : ABaseDataFragment<RepoListFragmentBinding>(), RepoListView {

    companion object {
        fun newInstance(): RepoListFragment = RepoListFragment()
    }

    @Inject
    lateinit var presenter: RepoListPresenter

    // Properties
    private fun getParam() = "lopspower"
    private val repoAdapter = ReposAdapter()

    // View Binding
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RepoListFragmentBinding =
        RepoListFragmentBinding::inflate

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
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = repoAdapter
    }

    //region INTENTS
    override fun intentLoadData(): Observable<String> =
        Observable.just(getParam())

    override fun intentRefreshData(): Observable<String> =
        binding.swipeRefreshLayout.refreshes().map { getParam() }

    override fun intentErrorRetry(): Observable<String> =
        btnErrorRetry?.clicks()?.map { getParam() } ?: Observable.never()

    override fun intentFavorite(): Observable<Pair<Int, Repo>> =
        repoAdapter.repoFavoriteIntent

    override fun openRepo(): Observable<Pair<Repo, String>> =
        repoAdapter.repoClickIntent.map { it to getParam() }
    //endregion

    //region RENDER
    override fun render(viewModel: RepoListViewModel) {
        TimberWrapper.d { "render: $viewModel" }

        showLoading(viewModel.loadingState == LoadingState.LOADING)
        showRefreshingLoading(binding.swipeRefreshLayout, false)
        showRetryLoading(viewModel.loadingState == LoadingState.RETRY)
        showContent(binding.content, viewModel.contentState == ContentState.CONTENT)
        showError(viewModel.contentState == ContentState.ERROR)

        renderData(viewModel.data)
        renderFavoriteRepo(viewModel.favoriteRepo, viewModel.favoriteRepoPosition)
        renderError(viewModel.errorMessage)
        renderSnack(viewModel.snackMessage)
    }

    private fun renderData(repoList: List<Repo>?) {
        repoList?.also {
            repoAdapter.setData(it)
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun renderFavoriteRepo(favoriteRepo: Repo?, favoriteRepoPosition: Int?) {
        if (favoriteRepo != null && favoriteRepoPosition != null) {
            repoAdapter.setData(favoriteRepoPosition, favoriteRepo)
        }
    }
    //endregion

}
